

# Build Docker image
docker build -t nursery-api .

# Run Docker container
docker run --env-file .env -p 8080:8080 nursery-api