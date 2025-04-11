#!/bin/bash

# Install required packages (if not already installed)
sudo yum update -y
sudo yum install git docker maven -y
sudo service docker start
sudo usermod -a -G docker ec2-user

# Clone the GitHub repository
git clone https://github.com/soham-shinde/nursery_multitenant_api.git

cd nursery_multitenant_api

# Build the Spring Boot project
./mvnw clean package -DskipTests

# Build Docker image
docker build -t nursery-api .

# Run Docker container
docker run --env-file .env -p 8080:8080 nursery-api