# Docker Setup Guide for SimplyFound Backend

## Quick Start

### Option 1: Docker Compose (Recommended - includes PostgreSQL)

```bash
# Build and start all services (backend + database)
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v
```

### Option 2: Docker Only (if you have external PostgreSQL)

```bash
# Build the image
docker build -t simplyfound-backend .

# Run the container
docker run -d \
  --name simplyfound-backend \
  -p 8383:8383 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/simplyfound \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=your-password \
  -v $(pwd)/uploads:/app/uploads \
  simplyfound-backend

# View logs
docker logs -f simplyfound-backend

# Stop container
docker stop simplyfound-backend

# Remove container
docker rm simplyfound-backend
```

## Environment Variables

You can override these at runtime:

- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_PROFILES_ACTIVE` - Spring profile (default: prod)
- `JAVA_OPTS` - JVM options (default: "-Xms256m -Xmx1024m")
- `SPRING_MAIL_USERNAME` - Email username
- `SPRING_MAIL_PASSWORD` - Email password

## Volumes

The `/app/uploads` directory is mounted as a volume to persist uploaded files:
- `uploads/images/` - Optional images
- `uploads/logos/` - Logo files

## Ports

- **8383** - Spring Boot application port

## Health Check

The backend will be available at: `http://localhost:8383`

API endpoints: `http://localhost:8383/api/*`

## Troubleshooting

### Check if container is running
```bash
docker ps
```

### View container logs
```bash
docker logs simplyfound-backend
```

### Access container shell
```bash
docker exec -it simplyfound-backend /bin/bash
```

### Rebuild after code changes
```bash
docker-compose build --no-cache backend
docker-compose up -d backend
```

