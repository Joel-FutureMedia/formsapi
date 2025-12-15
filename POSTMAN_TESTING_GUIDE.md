# Postman Testing Guide - SimplyFound Backend API

This guide provides step-by-step instructions for testing the Optional Images and Client Journey endpoints using Postman.

---

## Table of Contents
1. [Testing Client Journey Form Submission](#testing-client-journey-form-submission)
2. [Testing Optional Images CRUD](#testing-optional-images-crud)
3. [Testing Client Journey CRUD](#testing-client-journey-crud)
4. [Viewing Uploaded Files](#viewing-uploaded-files)

---

## Testing Client Journey Form Submission

### Endpoint: `POST /api/client-journeys/submit`

This endpoint accepts multipart/form-data to submit the complete form with file uploads.

### Step-by-Step Instructions:

1. **Open Postman** and create a new request
2. **Set Method**: `POST`
3. **Set URL**: `http://localhost:8080/api/client-journeys/submit`
4. **Set Body Type**: 
   - Go to **Body** tab
   - Select **form-data** (NOT raw or x-www-form-urlencoded)

5. **Add Form Fields** (Key-Value pairs):

#### Step 1 - Basic Information (Required)
```
Key: fullName                    Value: John Doe
Key: companyName                 Value: ABC Company
Key: email                       Value: john@example.com
Key: phoneNumber                 Value: +264123456789 (Optional)
Key: businessLocation           Value: Windhoek
```

#### Step 2 - Business Identity (Required)
```
Key: aboutInformation            Value: We provide excellent plumbing services in Windhoek
Key: industryId                  Value: 1 (Optional - must exist in database)
Key: logoFile                    Value: [Select File] - Choose an image file (Optional)
```

**Note for logoFile**: 
- Click on the key field and change the type from "Text" to "File" using the dropdown
- Click "Select Files" and choose an image file

#### Step 3 - Contact Information (Required)
```
Key: businessPhone              Value: +264123456789
Key: businessEmail               Value: business@example.com
Key: openingDaysId               Value: 1 (Optional - must exist in database)
Key: customHours                 Value: 9:00 AM - 5:00 PM (Optional)
```

#### Step 4 - Services/Products (Arrays)
```
Key: serviceNames                Value: House Painting
Key: serviceNames                Value: Interior Design
Key: serviceNames                Value: Exterior Renovation

Key: serviceDescriptions         Value: Professional interior and exterior painting services
Key: serviceDescriptions         Value: Modern interior design solutions
Key: serviceDescriptions         Value: Complete exterior renovation services
```

**Important**: 
- Add multiple `serviceNames` and `serviceDescriptions` fields
- They will be matched by index (first name with first description, etc.)
- You can add as many as you want

#### Step 5 - Visual Style (Optional)
```
Key: colorPaletteId              Value: 1 (Optional - must exist in database)
Key: layoutStyleId                Value: 1 (Optional - must exist in database)
Key: imageFiles                  Value: [Select Files] - Choose multiple image files (Optional, max 10)
```

**Note for imageFiles**:
- Click on the key field and change type to "File"
- You can select multiple files at once (hold Ctrl/Cmd while selecting)
- Maximum 10 images allowed

#### Step 6 - Social Links & Trust Builders (Optional)
```
Key: facebookUrl                 Value: https://facebook.com/abccompany
Key: instagramUrl                Value: https://instagram.com/abccompany
Key: googleBusinessUrl           Value: https://g.page/abccompany
Key: tiktokUrl                   Value: https://tiktok.com/@abccompany
Key: testimonials                Value: Great service! Highly recommended.
```

#### Step 7 - Calls to Action (Required)
```
Key: primaryCta                  Value: Call Now
Key: ctaLinkOrPhone              Value: +264123456789
Key: secondaryAction             Value: Get a Quote (Optional)
```

### Complete Example Request:

```
POST http://localhost:8080/api/client-journeys/submit

Body (form-data):
- fullName: John Doe
- companyName: ABC Company
- email: john@example.com
- phoneNumber: +264123456789
- businessLocation: Windhoek
- aboutInformation: We provide excellent plumbing services
- industryId: 1
- logoFile: [FILE] logo.png
- businessPhone: +264123456789
- businessEmail: business@example.com
- openingDaysId: 1
- serviceNames: House Painting
- serviceNames: Interior Design
- serviceDescriptions: Professional painting services
- serviceDescriptions: Modern design solutions
- colorPaletteId: 1
- layoutStyleId: 1
- imageFiles: [FILE] image1.jpg
- imageFiles: [FILE] image2.jpg
- facebookUrl: https://facebook.com/abc
- primaryCta: Call Now
- ctaLinkOrPhone: +264123456789
```

### Expected Response:
```json
{
  "id": 1,
  "fullName": "John Doe",
  "companyName": "ABC Company",
  "email": "john@example.com",
  "logoFileUrl": "/api/client-journeys/logos/view/logo_1234567890.png",
  "servicesOrProducts": [
    {
      "id": 1,
      "name": "House Painting",
      "description": "Professional painting services"
    }
  ],
  "optionalImages": [
    {
      "id": 1,
      "fileName": "image1_1234567890.jpg",
      "fileUrl": "/api/client-journeys/images/view/image1_1234567890.jpg"
    }
  ],
  "createdAt": "2025-01-15T10:30:00"
}
```

---

## Testing Optional Images CRUD

### 1. Upload Single Image (File Upload)

**Endpoint**: `POST /api/optional-images/upload`

**Method**: POST  
**URL**: `http://localhost:8080/api/optional-images/upload`

**Body** (form-data):
```
Key: file                    Value: [Select File] - Choose an image file
Key: clientJourneyId         Value: 1
```

**Steps in Postman**:
1. Select **Body** tab
2. Choose **form-data**
3. Add key `file` - Change type from "Text" to "File" using dropdown, then click "Select Files"
4. Add key `clientJourneyId` - Keep as "Text", enter the client journey ID (e.g., 1)

**Expected Response**:
```json
{
  "id": 1,
  "fileName": "image_1234567890.jpg",
  "fileUrl": "/api/client-journeys/images/view/image_1234567890.jpg",
  "fileType": "image/jpeg",
  "createdAt": "2025-01-15T10:30:00"
}
```

---

### 2. Upload Multiple Images (File Upload)

**Endpoint**: `POST /api/optional-images/upload-multiple`

**Method**: POST  
**URL**: `http://localhost:8080/api/optional-images/upload-multiple`

**Body** (form-data):
```
Key: files                  Value: [Select Files] - Choose multiple image files (hold Ctrl/Cmd)
Key: clientJourneyId         Value: 1
```

**Steps in Postman**:
1. Select **Body** tab
2. Choose **form-data**
3. Add key `files` - Change type to "File", then select multiple files at once
4. Add key `clientJourneyId` - Keep as "Text", enter the client journey ID

**Expected Response**:
```json
[
  {
    "id": 1,
    "fileName": "image1_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image1_1234567890.jpg",
    "fileType": "image/jpeg"
  },
  {
    "id": 2,
    "fileName": "image2_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image2_1234567890.jpg",
    "fileType": "image/jpeg"
  }
]
```

---

### 3. Create Optional Image (JSON - Alternative Method)

**Endpoint**: `POST /api/optional-images`

**Method**: POST  
**URL**: `http://localhost:8080/api/optional-images`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "fileName": "test-image.jpg",
  "fileUrl": "/api/client-journeys/images/view/test-image.jpg",
  "fileType": "image/jpeg",
  "clientJourney": {
    "id": 1
  }
}
```

**Note**: This is for manual creation. For file uploads, use the `/upload` or `/upload-multiple` endpoints above.

---

### 2. Get All Optional Images

**Endpoint**: `GET /api/optional-images`

**Method**: GET  
**URL**: `http://localhost:8080/api/optional-images`

**Expected Response**:
```json
[
  {
    "id": 1,
    "fileName": "image1_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image1_1234567890.jpg",
    "fileType": "image/jpeg",
    "createdAt": "2025-01-15T10:30:00"
  }
]
```

---

### 3. Get Optional Image by ID

**Endpoint**: `GET /api/optional-images/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/optional-images/1`

**Expected Response**:
```json
{
  "id": 1,
  "fileName": "image1_1234567890.jpg",
  "fileUrl": "/api/client-journeys/images/view/image1_1234567890.jpg",
  "fileType": "image/jpeg",
  "createdAt": "2025-01-15T10:30:00"
}
```

---

### 5. Get Optional Images by Client Journey ID

**Endpoint**: `GET /api/optional-images/client-journey/{clientJourneyId}`

**Method**: GET  
**URL**: `http://localhost:8080/api/optional-images/client-journey/1`

**Expected Response**:
```json
[
  {
    "id": 1,
    "fileName": "image1_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image1_1234567890.jpg",
    "fileType": "image/jpeg"
  },
  {
    "id": 2,
    "fileName": "image2_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image2_1234567890.jpg",
    "fileType": "image/jpeg"
  }
]
```

---

### 6. Update Optional Image

**Endpoint**: `PUT /api/optional-images/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/optional-images/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "fileName": "updated-image.jpg",
  "fileUrl": "/api/client-journeys/images/view/updated-image.jpg",
  "fileType": "image/jpeg"
}
```

---

### 7. Delete Optional Image

**Endpoint**: `DELETE /api/optional-images/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/optional-images/1`

**Expected Response**: 200 OK (no body)

---

## Testing Client Journey CRUD

### 1. Get All Client Journeys

**Endpoint**: `GET /api/client-journeys`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys`

**Expected Response**:
```json
[
  {
    "id": 1,
    "fullName": "John Doe",
    "companyName": "ABC Company",
    "email": "john@example.com",
    "servicesOrProducts": [...],
    "optionalImages": [...],
    "createdAt": "2025-01-15T10:30:00"
  }
]
```

---

### 2. Get Client Journey by ID

**Endpoint**: `GET /api/client-journeys/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys/1`

**Expected Response**: Full client journey object with all relationships loaded

---

### 3. Update Client Journey

**Endpoint**: `PUT /api/client-journeys/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/client-journeys/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "fullName": "Jane Doe",
  "companyName": "XYZ Company",
  "email": "jane@example.com",
  "businessLocation": "Swakopmund",
  "aboutInformation": "Updated business description",
  "businessPhone": "+264987654321",
  "businessEmail": "newemail@example.com",
  "primaryCta": "WhatsApp Us",
  "ctaLinkOrPhone": "+264987654321"
}
```

**Note**: Only include fields you want to update. Relationships (industry, colorPalette, etc.) should be included as objects with just the `id` field.

---

### 4. Delete Client Journey

**Endpoint**: `DELETE /api/client-journeys/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/client-journeys/1`

**Expected Response**: 200 OK (no body)

**Note**: This will also delete all associated services/products and optional images due to cascade delete.

---

## Viewing Uploaded Files

### View Logo

**Endpoint**: `GET /api/client-journeys/logos/view/{fileName}`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys/logos/view/logo_1234567890.png`

**Expected Response**: Image file (binary)

**In Postman**:
- The image will display in the response
- Or use "Send and Download" to save the file

---

### View Optional Image

**Endpoint**: `GET /api/client-journeys/images/view/{fileName}`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys/images/view/image1_1234567890.jpg`

**Expected Response**: Image file (binary)

**In Postman**:
- The image will display in the response
- Or use "Send and Download" to save the file

---

## Testing with cURL (Alternative)

### Submit Form with Files

```bash
curl -X POST http://localhost:8080/api/client-journeys/submit \
  -F "fullName=John Doe" \
  -F "companyName=ABC Company" \
  -F "email=john@example.com" \
  -F "businessLocation=Windhoek" \
  -F "aboutInformation=We provide excellent services" \
  -F "businessPhone=+264123456789" \
  -F "businessEmail=business@example.com" \
  -F "primaryCta=Call Now" \
  -F "ctaLinkOrPhone=+264123456789" \
  -F "serviceNames=Service 1" \
  -F "serviceNames=Service 2" \
  -F "serviceDescriptions=Description 1" \
  -F "serviceDescriptions=Description 2" \
  -F "logoFile=@/path/to/logo.png" \
  -F "imageFiles=@/path/to/image1.jpg" \
  -F "imageFiles=@/path/to/image2.jpg"
```

### Upload Single Image

```bash
curl -X POST http://localhost:8080/api/optional-images/upload \
  -F "file=@/path/to/image.jpg" \
  -F "clientJourneyId=1"
```

### Upload Multiple Images

```bash
curl -X POST http://localhost:8080/api/optional-images/upload-multiple \
  -F "files=@/path/to/image1.jpg" \
  -F "files=@/path/to/image2.jpg" \
  -F "files=@/path/to/image3.jpg" \
  -F "clientJourneyId=1"
```

### Get All Optional Images

```bash
curl -X GET http://localhost:8080/api/optional-images
```

### Get Optional Images by Client Journey

```bash
curl -X GET http://localhost:8080/api/optional-images/client-journey/1
```

### Get All Client Journeys

```bash
curl -X GET http://localhost:8080/api/client-journeys
```

---

## Common Issues and Solutions

### Issue: "Required request part 'fullName' is not present"
**Solution**: Make sure you're using `form-data` in Postman, not `raw` or `x-www-form-urlencoded`

### Issue: "File upload failed"
**Solution**: 
- Check file size (max 650MB per file)
- Ensure file is an image (jpg, png, gif, etc.)
- Check upload directory permissions

### Issue: "Industry not found"
**Solution**: Create the industry first using `POST /api/industries` before submitting the form

### Issue: Arrays not working
**Solution**: In Postman form-data, add multiple fields with the same key name (e.g., multiple `serviceNames` fields)

### Issue: CORS error
**Solution**: Ensure backend is running and CORS is enabled (already configured in controllers)

---

## Quick Test Checklist

- [ ] Backend is running on `http://localhost:8080`
- [ ] Database is connected and tables are created
- [ ] Test form submission with all required fields
- [ ] Test form submission with logo upload
- [ ] Test form submission with multiple images
- [ ] Test form submission with multiple services/products
- [ ] Verify email confirmation is sent
- [ ] Test viewing uploaded logo
- [ ] Test viewing uploaded images
- [ ] Test GET all client journeys
- [ ] Test GET client journey by ID
- [ ] Test GET optional images by client journey ID
- [ ] Test UPDATE client journey
- [ ] Test DELETE client journey

---

## Notes

1. **File Uploads**: Logo and images are uploaded separately in the form submission
2. **Arrays**: Services/products use arrays (`serviceNames[]` and `serviceDescriptions[]`)
3. **Email**: Confirmation email is sent automatically after form submission
4. **Relationships**: Client journey includes relationships with industry, color palette, layout style, etc.
5. **Cascade Delete**: Deleting a client journey will delete all associated services/products and images

---

## Support

For issues or questions, check:
- Backend logs for error messages
- Database connection in `application.properties`
- File upload paths and permissions
- Email configuration in `application.properties`

