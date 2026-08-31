// ---------------------------------------------------------------------------
// Point d'entree de l'infrastructure (scope souscription).
// Cree le groupe de ressources puis delegue le provisioning a resources.bicep.
// Utilise par Azure Developer CLI (azd up).
// ---------------------------------------------------------------------------
targetScope = 'subscription'

@minLength(1)
@maxLength(64)
@description('Nom de l\'environnement azd (prefixe des ressources, tag azd-env-name).')
param environmentName string

@minLength(1)
@description('Region Azure de deploiement (ex. westeurope).')
param location string

@description('Identifiant administrateur du serveur PostgreSQL.')
param postgresAdminLogin string = 'dofusadmin'

@secure()
@description('Mot de passe administrateur PostgreSQL (fourni via la variable POSTGRES_PASSWORD).')
param postgresAdminPassword string

var tags = { 'azd-env-name': environmentName }
var resourceToken = toLower(uniqueString(subscription().id, environmentName, location))

resource rg 'Microsoft.Resources/resourceGroups@2024-03-01' = {
  name: 'rg-${environmentName}'
  location: location
  tags: tags
}

module resources 'resources.bicep' = {
  name: 'resources'
  scope: rg
  params: {
    location: location
    tags: tags
    resourceToken: resourceToken
    postgresAdminLogin: postgresAdminLogin
    postgresAdminPassword: postgresAdminPassword
  }
}

// Sorties consommees par azd et la CI.
output AZURE_CONTAINER_REGISTRY_ENDPOINT string = resources.outputs.registryLoginServer
output AZURE_CONTAINER_REGISTRY_NAME string = resources.outputs.registryName
output AZURE_RESOURCE_GROUP string = rg.name
output BACKEND_FQDN string = resources.outputs.backendFqdn
output FRONTEND_URL string = resources.outputs.frontendUrl
