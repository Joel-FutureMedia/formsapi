# SimplyFound - Website Builder System

A full-stack application for collecting client information and generating website development prompts.

## Project Structure

- **Backend**: Spring Boot REST API (`simplyfound`)
- **Frontend**: React application (`simplyfoundforms`)

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL database
- Node.js 16+ and npm
- Gmail account (for email configuration)

## Backend Setup (Spring Boot)

### 1. Database Configuration

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/simplyfound
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Email Configuration

Update email settings in `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Note**: For Gmail, you need to generate an App Password:
1. Go to Google Account settings
2. Enable 2-Step Verification
3. Generate an App Password for "Mail"

### 3. File Upload Paths

The application will create upload directories automatically:
- `uploads/images/` - For optional images
- `uploads/logos/` - For business logos

### 4. Build and Run

**EASIEST WAY**: Double-click `START.bat` in the project root folder.

**OR** run this command:
```bash
cd simplyfound
mvn spring-boot:run
```

The `pom.xml` is configured with JVM arguments, so it should work automatically.

**If you still get file count errors**, use:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dorg.apache.tomcat.util.http.fileupload.FileUploadBase.FileCountLimit=10000 -Dorg.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeMax=681574400"
```

The backend will run on `http://localhost:8080`

**Note**: See `QUICK_FIX.md` for troubleshooting file upload errors.

## Frontend Setup (React)

### 1. Install Dependencies

```bash
cd simplyfoundforms
npm install
```

### 2. Run Development Server

```bash
npm start
```

The frontend will run on `http://localhost:3000`

**Note**: To access the admin panel, navigate to `http://localhost:3000/admin`

## API Endpoints

### Reference Data
- `GET /api/reference-data` - Get all reference data (industries, color palettes, etc.)

### Client Journey
- `POST /api/client-journeys/submit` - Submit form
- `GET /api/client-journeys` - Get all submissions
- `GET /api/client-journeys/{id}` - Get submission by ID
- `GET /api/client-journeys/{id}/download-prompt` - Download prompt PDF
- `GET /api/client-journeys/{id}/download-project-files` - Download project files (ZIP)

### Authentication
- `POST /api/auth/login` - Admin login (params: email, password)

### CRUD Endpoints

Each model has full CRUD endpoints:
- `GET /api/{resource}` - List all
- `GET /api/{resource}/{id}` - Get by ID
- `POST /api/{resource}` - Create
- `PUT /api/{resource}/{id}` - Update
- `DELETE /api/{resource}/{id}` - Delete

Resources:
- `/api/industries`
- `/api/color-palettes`
- `/api/layout-styles`
- `/api/opening-days`
- `/api/opening-hours`
- `/api/services-or-products`
- `/api/optional-images`
- `/api/users`

## Testing the API

### 1. Create Admin User

First, create an admin user via API:

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "admin123",
    "role": "ADMIN"
  }'
```

### 2. Test Form Submission

Use Postman or curl to test form submission:

```bash
curl -X POST http://localhost:8080/api/client-journeys/submit \
  -F "fullname=John Doe" \
  -F "companyName=Test Company" \
  -F "email=john@example.com" \
  -F "businessLocation=Windhoek" \
  -F "aboutInformation=We provide excellent services" \
  -F "businessEmail=business@example.com" \
  -F "businessPhone=+264123456789" \
  -F "primaryCta=Call Now" \
  -F "ctaLinkOrPhone=+264123456789"
```

### 3. Test Admin Login

```bash
curl -X POST "http://localhost:8080/api/auth/login?email=admin@example.com&password=admin123"
```

### 4. Download Prompt PDF

```bash
curl -X GET http://localhost:8080/api/client-journeys/1/download-prompt \
  --output prompt.pdf
```

### 5. Download Project Files

```bash
curl -X GET http://localhost:8080/api/client-journeys/1/download-project-files \
  --output project-files.zip
```

## Database Schema

The application uses JPA/Hibernate with auto-update enabled. Tables will be created automatically:

- `users` - Admin users
- `clientjourney` - Main form submissions
- `industry` - Industry types
- `color_palettes` - Color palette options
- `layoutstyle` - Layout style options
- `openingdays` - Opening days options
- `openinghours` - Opening hours options
- `services_or_products` - Services/products for each submission
- `optional_images` - Optional images for each submission

## Seeding Reference Data

You can seed reference data using the API endpoints. Example:

```bash
# Create Industries
curl -X POST http://localhost:8080/api/industries -H "Content-Type: application/json" -d '{"industry": "Agriculture"}'
curl -X POST http://localhost:8080/api/industries -H "Content-Type: application/json" -d '{"industry": "Retail"}'
curl -X POST http://localhost:8080/api/industries -H "Content-Type: application/json" -d '{"industry": "Services"}'

# Create Color Palettes
curl -X POST http://localhost:8080/api/color-palettes -H "Content-Type: application/json" -d '{"name": "Palette 1", "color1": "#FF5733", "color2": "#33FF57", "color3": "#3357FF"}'

# Create Layout Styles
curl -X POST http://localhost:8080/api/layout-styles -H "Content-Type: application/json" -d '{"style": "Clean & Minimal"}'
curl -X POST http://localhost:8080/api/layout-styles -H "Content-Type: application/json" -d '{"style": "Corporate & Professional"}'
curl -X POST http://localhost:8080/api/layout-styles -H "Content-Type: application/json" -d '{"style": "Bold & Modern"}'

# Create Opening Days
curl -X POST http://localhost:8080/api/opening-days -H "Content-Type: application/json" -d '{"openingDays": "Mon – Fri"}'
curl -X POST http://localhost:8080/api/opening-days -H "Content-Type: application/json" -d '{"openingDays": "Mon – Sat"}'
curl -X POST http://localhost:8080/api/opening-days -H "Content-Type: application/json" -d '{"openingDays": "7 Days a Week"}'

# Create Opening Hours
curl -X POST http://localhost:8080/api/opening-hours -H "Content-Type: application/json" -d '{"openingHours": "9:00 AM - 5:00 PM"}'
curl -X POST http://localhost:8080/api/opening-hours -H "Content-Type: application/json" -d '{"openingHours": "8:00 AM - 6:00 PM"}'
```

## Features

### Form Features
- 7-step multi-step form
- File upload for logo and images
- Dynamic service/product addition
- Color palette and layout style selection
- Social media links and testimonials
- Email confirmation on submission

### Admin Panel Features
- Simple email/password login
- View all client submissions
- Download prompt PDF files
- Download project files (logo + images) as ZIP

### PDF Prompt Features
- Well-structured website development instructions
- All client information included
- Design requirements and technical specifications
- British English spelling requirements
- Responsive design guidelines
- SEO and accessibility requirements

## Troubleshooting

### Backend Issues

1. **Database Connection Error**
   - Check PostgreSQL is running
   - Verify credentials in `application.properties`
   - Ensure database `simplyfound` exists

2. **Email Not Sending**
   - Verify Gmail credentials
   - Check App Password is correct
   - Ensure 2-Step Verification is enabled

3. **File Upload Errors**
   - Check disk space
   - Verify upload directory permissions
   - Check file size limits in `application.properties`

### Frontend Issues

1. **API Connection Error**
   - Ensure backend is running on port 8080
   - Check CORS settings (already configured)
   - Verify API_BASE_URL in components

2. **Build Errors**
   - Run `npm install` again
   - Clear node_modules and reinstall
   - Check Node.js version (16+)

## Development Notes

- The backend uses Spring Boot 4.0.0
- The frontend uses React 18.2.0
- All API endpoints support CORS from any origin (configured for development)
- File uploads are stored locally in `uploads/` directory
- PDF generation uses iText7 library
- Email templates use HTML formatting

## License

This project is proprietary software.

