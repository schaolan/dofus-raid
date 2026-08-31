# Build stage
FROM node:20-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
# URL de l'API cote navigateur. Vide (defaut) = appels relatifs same-origin,
# proxifies par nginx vers le backend -> pas de CORS, pas d'URL figee au build.
ARG VITE_API_URL=""
ENV VITE_API_URL=$VITE_API_URL
RUN npm run build

# Run stage
FROM nginx:alpine
# Le template est traite par envsubst au demarrage (docker-entrypoint nginx) :
# ${BACKEND_URL} est injectee dans /etc/nginx/conf.d/default.conf.
COPY nginx.conf.template /etc/nginx/templates/default.conf.template
# Cible backend par defaut (surchargee en prod / compose). Restreint envsubst a
# cette seule variable pour ne pas toucher aux variables nginx.
ENV BACKEND_URL=http://backend:8080
ENV NGINX_ENVSUBST_FILTER=BACKEND_URL
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
