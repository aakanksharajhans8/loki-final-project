# Use Node.js 22
FROM node:22

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 5173

# Set environment variable for HMR host (Minikube IP)
ENV HMR_HOST=192.168.49.2

CMD ["npm", "run", "dev"]
