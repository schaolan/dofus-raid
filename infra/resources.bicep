// ---------------------------------------------------------------------------
// Ressources applicatives (scope groupe de ressources) :
//   Log Analytics, environnement Container Apps, ACR, identite managee,
//   PostgreSQL Flexible Server, et les 2 Container Apps (backend + frontend).
// ---------------------------------------------------------------------------

@description('Region de deploiement.')
param location string

@description('Tags appliques a toutes les ressources.')
param tags object

@description('Suffixe unique et deterministe pour nommer les ressources.')
param resourceToken string

@description('Identifiant administrateur PostgreSQL.')
param postgresAdminLogin string

@secure()
@description('Mot de passe administrateur PostgreSQL.')
param postgresAdminPassword string

// Role integre AcrPull (permet aux Container Apps de tirer les images depuis l'ACR).
var acrPullRoleId = subscriptionResourceId('Microsoft.Authorization/roleDefinitions', '7f951dda-4ed3-4680-a7ca-43fe172d538d')

var backendAppName = 'ca-backend-${resourceToken}'
var frontendAppName = 'ca-frontend-${resourceToken}'

// Image temporaire utilisee a la creation ; remplacee par `azd deploy`.
var placeholderImage = 'mcr.microsoft.com/azuredocs/containerapps-helloworld:latest'

// --- Observabilite -----------------------------------------------------------
resource logAnalytics 'Microsoft.OperationalInsights/workspaces@2023-09-01' = {
  name: 'log-${resourceToken}'
  location: location
  tags: tags
  properties: {
    sku: { name: 'PerGB2018' }
    retentionInDays: 30
  }
}

// --- Registre de conteneurs --------------------------------------------------
resource registry 'Microsoft.ContainerRegistry/registries@2023-11-01-preview' = {
  name: 'acr${resourceToken}'
  location: location
  tags: tags
  sku: { name: 'Basic' }
  properties: {
    adminUserEnabled: false
  }
}

// --- Identite managee pour tirer les images ---------------------------------
resource identity 'Microsoft.ManagedIdentity/userAssignedIdentities@2023-01-31' = {
  name: 'id-${resourceToken}'
  location: location
  tags: tags
}

resource acrPull 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(registry.id, identity.id, acrPullRoleId)
  scope: registry
  properties: {
    roleDefinitionId: acrPullRoleId
    principalId: identity.properties.principalId
    principalType: 'ServicePrincipal'
  }
}

// --- Environnement Container Apps -------------------------------------------
resource containerEnv 'Microsoft.App/managedEnvironments@2024-03-01' = {
  name: 'cae-${resourceToken}'
  location: location
  tags: tags
  properties: {
    appLogsConfiguration: {
      destination: 'log-analytics'
      logAnalyticsConfiguration: {
        customerId: logAnalytics.properties.customerId
        sharedKey: logAnalytics.listKeys().primarySharedKey
      }
    }
  }
}

// --- Base de donnees PostgreSQL managee -------------------------------------
resource postgres 'Microsoft.DBforPostgreSQL/flexibleServers@2024-08-01' = {
  name: 'psql-${resourceToken}'
  location: location
  tags: tags
  sku: {
    name: 'Standard_B1ms'
    tier: 'Burstable'
  }
  properties: {
    version: '16'
    administratorLogin: postgresAdminLogin
    administratorLoginPassword: postgresAdminPassword
    storage: { storageSizeGB: 32 }
    backup: { backupRetentionDays: 7, geoRedundantBackup: 'Disabled' }
    highAvailability: { mode: 'Disabled' }
    network: { publicNetworkAccess: 'Enabled' }
    authConfig: { activeDirectoryAuth: 'Disabled', passwordAuth: 'Enabled' }
  }
}

// Autorise les services Azure (dont les Container Apps) a joindre le serveur.
resource pgFirewallAzure 'Microsoft.DBforPostgreSQL/flexibleServers/firewallRules@2024-08-01' = {
  parent: postgres
  name: 'AllowAllAzureServices'
  properties: {
    startIpAddress: '0.0.0.0'
    endIpAddress: '0.0.0.0'
  }
}

resource pgDatabase 'Microsoft.DBforPostgreSQL/flexibleServers/databases@2024-08-01' = {
  parent: postgres
  name: 'dofusraid'
  properties: {
    charset: 'UTF8'
    collation: 'en_US.utf8'
  }
}

// FQDN externe previsible du frontend (pour le CORS backend, meme si le proxy
// nginx rend le CORS inutile en pratique).
var frontendUrl = 'https://${frontendAppName}.${containerEnv.properties.defaultDomain}'

// --- Container App : backend (ingress interne) ------------------------------
resource backend 'Microsoft.App/containerApps@2024-03-01' = {
  name: backendAppName
  location: location
  tags: union(tags, { 'azd-service-name': 'backend' })
  identity: {
    type: 'UserAssigned'
    userAssignedIdentities: {
      '${identity.id}': {}
    }
  }
  properties: {
    managedEnvironmentId: containerEnv.id
    configuration: {
      activeRevisionsMode: 'Single'
      ingress: {
        external: false
        targetPort: 8080
        transport: 'auto'
      }
      registries: [
        {
          server: registry.properties.loginServer
          identity: identity.id
        }
      ]
      secrets: [
        {
          name: 'db-password'
          value: postgresAdminPassword
        }
      ]
    }
    template: {
      containers: [
        {
          name: 'backend'
          image: placeholderImage
          resources: {
            cpu: json('0.5')
            memory: '1Gi'
          }
          env: [
            {
              name: 'SPRING_DATASOURCE_URL'
              value: 'jdbc:postgresql://${postgres.properties.fullyQualifiedDomainName}:5432/dofusraid?sslmode=require'
            }
            {
              name: 'SPRING_DATASOURCE_USERNAME'
              value: postgresAdminLogin
            }
            {
              name: 'SPRING_DATASOURCE_PASSWORD'
              secretRef: 'db-password'
            }
            {
              name: 'APP_CORS_ALLOWED_ORIGINS'
              value: frontendUrl
            }
          ]
        }
      ]
      scale: {
        // Scale-to-zero : plus aucune replica facturee apres ~5 min d'inactivite.
        // Contrepartie : cold start sur la premiere requete (demarrage JVM +
        // Spring Boot + Flyway, de l'ordre de 15-30 s). Les timeouts du proxy
        // nginx du frontend sont dimensionnes en consequence.
        // Pour un environnement ou la latence importe : repasser a 1.
        minReplicas: 0
        maxReplicas: 2
        rules: [
          {
            name: 'http'
            http: {
              metadata: {
                concurrentRequests: '20'
              }
            }
          }
        ]
      }
    }
  }
  dependsOn: [
    acrPull
    pgDatabase
    pgFirewallAzure
  ]
}

// --- Container App : frontend (ingress externe) -----------------------------
resource frontend 'Microsoft.App/containerApps@2024-03-01' = {
  name: frontendAppName
  location: location
  tags: union(tags, { 'azd-service-name': 'frontend' })
  identity: {
    type: 'UserAssigned'
    userAssignedIdentities: {
      '${identity.id}': {}
    }
  }
  properties: {
    managedEnvironmentId: containerEnv.id
    configuration: {
      activeRevisionsMode: 'Single'
      ingress: {
        external: true
        targetPort: 80
        transport: 'auto'
      }
      registries: [
        {
          server: registry.properties.loginServer
          identity: identity.id
        }
      ]
    }
    template: {
      containers: [
        {
          name: 'frontend'
          image: placeholderImage
          resources: {
            cpu: json('0.25')
            memory: '0.5Gi'
          }
          env: [
            {
              // nginx proxifie /api vers l'ingress interne du backend (HTTPS).
              name: 'BACKEND_URL'
              value: 'https://${backend.properties.configuration.ingress.fqdn}'
            }
          ]
        }
      ]
      scale: {
        // Scale-to-zero. Cold start nginx negligeable (~1-3 s), mais la premiere
        // requete /api/ derriere reveille aussi le backend (voir ci-dessus).
        minReplicas: 0
        maxReplicas: 2
        rules: [
          {
            name: 'http'
            http: {
              metadata: {
                concurrentRequests: '50'
              }
            }
          }
        ]
      }
    }
  }
  dependsOn: [
    acrPull
  ]
}

output registryLoginServer string = registry.properties.loginServer
output registryName string = registry.name
output backendFqdn string = backend.properties.configuration.ingress.fqdn
output frontendUrl string = 'https://${frontend.properties.configuration.ingress.fqdn}'
