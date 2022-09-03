FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=node /app/dist/pension-management-portal /usr/share/nginx/html
ENTRYPOINT ["nginx", "-g", "daemon off;"]
