# CONFIGURACION DEL NODE VERSION 14
# FROM node:latest
FROM node:14.15-alpine as build-admin
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app
RUN npm install
COPY . /app
RUN npm run build --prod

# CONFIGURACION DEL SERVIDOR NGINX
#FROM nginx:alpine
FROM nginx:1.17.1-alpine
EXPOSE 80
COPY --from=build-admin /app/dist/sys-botica-admin /usr/share/nginx/html
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf
CMD ["nginx","-g","daemon off;"]

