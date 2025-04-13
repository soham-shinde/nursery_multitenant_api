docker rmi $(docker images -q)



git pull origin main


cp ../.env ./  


# Build Docker image
docker build -t nursery-api .

# Run Docker container
# docker run --env-file .env -p 8080:8080 nursery-api

docker run --env-file .env -p 8080:8080 -d --name nursery-api-container nursery-api
