#ETAPA 1 BUILD
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .

#PARTE PARA LOS ARGS DEL COMPOSE
ARG API_HOST
ARG API_BASE
ARG API_PORT

#variables de entorno segun los arg
ENV VITE_API_HOST=$API_HOST
ENV VITE_API_BASE=$API_BASE
ENV VITE_API_PORT=$API_PORT

RUN npm run build

#ETAPA 2 CONSTRUCCIÓN DE IMAGEN
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]