# Specify Base Image
FROM docker.io/nodemon:latest
# Work Directory
WORKDIR /usr/app
# COPY Files
COPY package.json .
COPY nodemon.json .
COPY package-lock.json .
# Install Dependecies
RUN npm install
COPY ./ ./
# Startup Command
CMD ["npm", "start"]