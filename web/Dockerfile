FROM node:alpine as builder

WORKDIR /app
COPY package.json ./
COPY . .
RUN npm install
RUN npm run build

FROM nginx:stable-alpine
COPY --from=builder /app/dist/spa /usr/share/nginx/html
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]
