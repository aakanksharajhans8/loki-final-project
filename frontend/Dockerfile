FROM node:22
WORKDIR /app

# Install dependencies first
COPY package*.json ./
RUN npm install

# Copy the rest of the code
COPY . .

EXPOSE 3000
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "3000"]
