docker stop nursery-api-container

docker rm nursery-api-container

git pull origin main


cp ../.env ./  


# Build Docker image
docker build -t nursery-api .


docker run --env-file .env -p 8080:8080 -d --name nursery-api-container nursery-api
