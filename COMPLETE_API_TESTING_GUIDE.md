# Complete API Testing Guide - SimplyFound Backend

This guide provides complete sample data and step-by-step instructions for testing ALL APIs in Postman.

**Base URL**: `http://localhost:8080`

---

## Table of Contents

1. [User APIs](#1-user-apis)
2. [Industry APIs](#2-industry-apis)
3. [Color Palette APIs](#3-color-palette-apis)
4. [Layout Style APIs](#4-layout-style-apis)
5. [Opening Days APIs](#5-opening-days-apis)
6. [Opening Hours APIs](#6-opening-hours-apis)
7. [Client\
8. 
9. Journey APIs](#7-client-journey-apis)
8. [Service/Product APIs](#8-serviceproduct-apis)
9. [Optional Image APIs](#9-optional-image-apis)
10. [Authentication](#10-authentication)

---

## 1. User APIs

### 1.1 Create User

**Endpoint**: `POST /api/users`

**Method**: POST  
**URL**: `http://localhost:8080/api/users`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "email": "admin@simplyfound.com",
  "password": "admin123",
  "name": "Admin User",
  "role": "ADMIN"
}
```

**Expected Response**:
```json
{
  "id": 1,
  "email": "admin@simplyfound.com",
  "password": "admin123",
  "name": "Admin User",
  "role": "ADMIN",
  "createdAt": "2025-01-15T10:00:00"
}
```

---

### 1.2 Get All Users

**Endpoint**: `GET /api/users`

**Method**: GET  
**URL**: `http://localhost:8080/api/users`

**Expected Response**:
```json
[
  {
    "id": 1,
    "email": "admin@simplyfound.com",
    "name": "Admin User",
    "role": "ADMIN"
  }
]
```

---

### 1.3 Get User by ID

**Endpoint**: `GET /api/users/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/users/1`

---

### 1.4 Update User

**Endpoint**: `PUT /api/users/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/users/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "email": "admin.updated@simplyfound.com",
  "password": "newpassword123",
  "name": "Updated Admin",
  "role": "ADMIN"
}
```

---

### 1.5 Delete User

**Endpoint**: `DELETE /api/users/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/users/1`

---

## 2. Industry APIs

### 2.1 Create Industry

**Endpoint**: `POST /api/industries`

**Method**: POST  
**URL**: `http://localhost:8080/api/industries`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "industry": "Agriculture"
}
```

**More Sample Industries**:
```json
{"industry": "Retail"}
{"industry": "Services"}
{"industry": "Food & Beverage"}
{"industry": "Beauty"}
{"industry": "Automotive"}
{"industry": "Construction"}
{"industry": "Healthcare"}
{"industry": "Technology"}
{"industry": "Education"}
{"industry": "Real Estate"}
```

**Expected Response**:
```json
{
  "id": 1,
  "industry": "Agriculture",
  "createdAt": "2025-01-15T10:00:00"
}
```

---

### 2.2 Get All Industries

**Endpoint**: `GET /api/industries`

**Method**: GET  
**URL**: `http://localhost:8080/api/industries`

---

### 2.3 Get Industry by ID

**Endpoint**: `GET /api/industries/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/industries/1`

---

### 2.4 Update Industry

**Endpoint**: `PUT /api/industries/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/industries/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "industry": "Agriculture & Farming"
}
```

---

### 2.5 Delete Industry

**Endpoint**: `DELETE /api/industries/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/industries/1`

---

## 3. Color Palette APIs

### 3.1 Create Color Palette

**Endpoint**: `POST /api/color-palettes`

**Method**: POST  
**URL**: `http://localhost:8080/api/color-palettes`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "name": "Ocean Blue",
  "color1": "#1E3A8A",
  "color2": "#3B82F6",
  "color3": "#60A5FA",
  "color4": "#DBEAFE",
  "color5": "#EFF6FF",
  "description": "A calming blue palette perfect for professional websites",
  "category": "Professional",
  "isPredefined": true,
  "displayOrder": 1
}
```

**More Sample Color Palettes**:
```json
{
  "name": "Forest Green",
  "color1": "#14532D",
  "color2": "#22C55E",
  "color3": "#4ADE80",
  "color4": "#D1FAE5",
  "color5": "#F0FDF4",
  "description": "Natural green tones for eco-friendly and nature-themed sites",
  "category": "Nature",
  "isPredefined": true,
  "displayOrder": 2
}
```

```json
{
  "name": "Sunset Orange",
  "color1": "#7C2D12",
  "color2": "#F97316",
  "color3": "#FB923C",
  "color4": "#FFEDD5",
  "color5": "#FFF7ED",
  "description": "Warm orange palette for creative and energetic brands",
  "category": "Creative",
  "isPredefined": true,
  "displayOrder": 3
}
```

```json
{
  "name": "Royal Purple",
  "color1": "#581C87",
  "color2": "#A855F7",
  "color3": "#C084FC",
  "color4": "#F3E8FF",
  "color5": "#FAF5FF",
  "description": "Elegant purple tones for luxury and premium brands",
  "category": "Luxury",
  "isPredefined": true,
  "displayOrder": 4
}
```

**Expected Response**:
```json
{
  "id": 1,
  "name": "Ocean Blue",
  "color1": "#1E3A8A",
  "color2": "#3B82F6",
  "color3": "#60A5FA",
  "color4": "#DBEAFE",
  "color5": "#EFF6FF",
  "description": "A calming blue palette perfect for professional websites",
  "category": "Professional",
  "isPredefined": true,
  "displayOrder": 1,
  "createdAt": "2025-01-15T10:00:00"
}
```

---

### 3.2 Get All Color Palettes

**Endpoint**: `GET /api/color-palettes`

**Method**: GET  
**URL**: `http://localhost:8080/api/color-palettes`

---

### 3.2a Get Color Palettes for Picker (Ordered)

**Endpoint**: `GET /api/color-palettes/picker`

**Method**: GET  
**URL**: `http://localhost:8080/api/color-palettes/picker`

**Description**: Returns all color palettes ordered by displayOrder and name. Perfect for populating a color palette picker UI.

**Expected Response**:
```json
[
  {
    "id": 1,
    "name": "Ocean Blue",
    "color1": "#1E3A8A",
    "color2": "#3B82F6",
    "color3": "#60A5FA",
    "color4": "#DBEAFE",
    "color5": "#EFF6FF",
    "description": "A calming blue palette...",
    "category": "Professional",
    "displayOrder": 1
  }
]
```

---

### 3.2b Get Predefined Color Palettes

**Endpoint**: `GET /api/color-palettes/predefined`

**Method**: GET  
**URL**: `http://localhost:8080/api/color-palettes/predefined`

**Description**: Returns only predefined color palettes (isPredefined = true). Useful for showing default options in the picker.

---

### 3.2c Get Color Palettes by Category

**Endpoint**: `GET /api/color-palettes/category/{category}`

**Method**: GET  
**URL**: `http://localhost:8080/api/color-palettes/category/Professional`

**Description**: Returns color palettes filtered by category. Useful for filtering in the picker UI.

**Example URLs**:
- `http://localhost:8080/api/color-palettes/category/Professional`
- `http://localhost:8080/api/color-palettes/category/Nature`
- `http://localhost:8080/api/color-palettes/category/Creative`

---

### 3.2d Get All Categories

**Endpoint**: `GET /api/color-palettes/categories`

**Method**: GET  
**URL**: `http://localhost:8080/api/color-palettes/categories`

**Description**: Returns a list of all unique categories. Useful for building category filters in the picker UI.

**Expected Response**:
```json
[
  "Creative",
  "Luxury",
  "Nature",
  "Professional"
]
```

---

### 3.3 Get Color Palette by ID

**Endpoint**: `GET /api/color-palettes/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/color-palettes/1`

---

### 3.4 Update Color Palette

**Endpoint**: `PUT /api/color-palettes/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/color-palettes/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "name": "Deep Ocean Blue",
  "color1": "#0F172A",
  "color2": "#1E40AF",
  "color3": "#3B82F6",
  "color4": "#DBEAFE",
  "color5": "#EFF6FF",
  "description": "Updated description",
  "category": "Professional",
  "isPredefined": true,
  "displayOrder": 1
}
```

---

### 3.5 Delete Color Palette

**Endpoint**: `DELETE /api/color-palettes/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/color-palettes/1`

---

## 4. Layout Style APIs

### 4.1 Create Layout Style

**Endpoint**: `POST /api/layout-styles`

**Method**: POST  
**URL**: `http://localhost:8080/api/layout-styles`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "style": "Clean & Minimal"
}
```

**More Sample Layout Styles**:
```json
{"style": "Corporate & Professional"}
{"style": "Bold & Modern"}
{"style": "Creative & Artistic"}
{"style": "Elegant & Classic"}
```

**Expected Response**:
```json
{
  "id": 1,
  "style": "Clean & Minimal",
  "createdAt": "2025-01-15T10:00:00"
}
```

---

### 4.2 Get All Layout Styles

**Endpoint**: `GET /api/layout-styles`

**Method**: GET  
**URL**: `http://localhost:8080/api/layout-styles`

---

### 4.3 Get Layout Style by ID

**Endpoint**: `GET /api/layout-styles/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/layout-styles/1`

---

### 4.4 Update Layout Style

**Endpoint**: `PUT /api/layout-styles/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/layout-styles/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "style": "Ultra Clean & Minimal"
}
```

---

### 4.5 Delete Layout Style

**Endpoint**: `DELETE /api/layout-styles/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/layout-styles/1`

---

## 5. Opening Days APIs

### 5.1 Create Opening Days

**Endpoint**: `POST /api/opening-days`

**Method**: POST  
**URL**: `http://localhost:8080/api/opening-days`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "openingDays": "Mon – Fri"
}
```

**More Sample Opening Days**:
```json
{"openingDays": "Mon – Sat"}
{"openingDays": "7 Days a Week"}
{"openingDays": "Weekdays Only"}
{"openingDays": "Weekends Only"}
```

**Expected Response**:
```json
{
  "id": 1,
  "openingDays": "Mon – Fri",
  "createdAt": "2025-01-15T10:00:00"
}
```

---

### 5.2 Get All Opening Days

**Endpoint**: `GET /api/opening-days`

**Method**: GET  
**URL**: `http://localhost:8080/api/opening-days`

---

### 5.3 Get Opening Days by ID

**Endpoint**: `GET /api/opening-days/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/opening-days/1`

---

### 5.4 Update Opening Days

**Endpoint**: `PUT /api/opening-days/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/opening-days/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "openingDays": "Monday to Friday"
}
```

---

### 5.5 Delete Opening Days

**Endpoint**: `DELETE /api/opening-days/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/opening-days/1`

---

## 6. Opening Hours APIs

### 6.1 Create Opening Hours

**Endpoint**: `POST /api/opening-hours`

**Method**: POST  
**URL**: `http://localhost:8080/api/opening-hours`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "openingHours": "9:00 AM - 5:00 PM"
}
```

**More Sample Opening Hours**:
```json
{"openingHours": "8:00 AM - 6:00 PM"}
{"openingHours": "10:00 AM - 4:00 PM"}
{"openingHours": "24 Hours"}
{"openingHours": "6:00 AM - 10:00 PM"}
```

**Expected Response**:
```json
{
  "id": 1,
  "openingHours": "9:00 AM - 5:00 PM",
  "createdAt": "2025-01-15T10:00:00"
}
```

---

### 6.2 Get All Opening Hours

**Endpoint**: `GET /api/opening-hours`

**Method**: GET  
**URL**: `http://localhost:8080/api/opening-hours`

---

### 6.3 Get Opening Hours by ID

**Endpoint**: `GET /api/opening-hours/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/opening-hours/1`

---

### 6.4 Update Opening Hours

**Endpoint**: `PUT /api/opening-hours/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/opening-hours/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "openingHours": "8:30 AM - 5:30 PM"
}
```

---

### 6.5 Delete Opening Hours

**Endpoint**: `DELETE /api/opening-hours/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/opening-hours/1`

---

## 7. Client Journey APIs

### 7.1 Submit Complete Form (with File Uploads)

**Endpoint**: `POST /api/client-journeys/submit`

**Method**: POST  
**URL**: `http://localhost:8080/api/client-journeys/submit`

**Body Type**: **form-data** (NOT raw JSON)

#### Step-by-Step in Postman:

1. Select **Body** tab
2. Choose **form-data**
3. Add the following fields:

**Required Fields**:
```
Key: fullName                    Value: John Doe
Key: companyName                 Value: ABC Plumbing Services
Key: email                       Value: john@abcplumbing.com
Key: businessLocation           Value: Windhoek, Namibia
Key: aboutInformation            Value: We provide professional plumbing services including installation, repair, and maintenance for residential and commercial properties.
Key: businessPhone               Value: +264 61 123 4567
Key: businessEmail               Value: info@abcplumbing.com
Key: primaryCta                  Value: Call Now
Key: ctaLinkOrPhone              Value: +264 61 123 4567
```

**Optional Fields**:
```
Key: phoneNumber                 Value: +264 81 123 4567
Key: industryId                  Value: 1
Key: logoFile                    Value: [FILE] - Select logo image file
Key: openingDaysId               Value: 1
Key: customHours                 Value: 8:00 AM - 6:00 PM
Key: colorPaletteId              Value: 1
Key: layoutStyleId               Value: 1
Key: facebookUrl                 Value: https://facebook.com/abcplumbing
Key: instagramUrl                Value: https://instagram.com/abcplumbing
Key: googleBusinessUrl           Value: https://g.page/abcplumbing
Key: tiktokUrl                   Value: https://tiktok.com/@abcplumbing
Key: testimonials               Value: "Excellent service! They fixed my leaky pipe in no time. Highly recommended!" - Sarah M.
Key: secondaryAction             Value: Get a Quote
```

**Array Fields** (Add multiple entries with same key):
```
Key: serviceNames                Value: Pipe Installation
Key: serviceNames                Value: Leak Repair
Key: serviceNames                Value: Drain Cleaning
Key: serviceNames                Value: Water Heater Service

Key: serviceDescriptions         Value: Professional installation of pipes for new construction and renovations
Key: serviceDescriptions         Value: Quick and reliable repair of leaks and burst pipes
Key: serviceDescriptions         Value: Thorough cleaning and unclogging of drains and sewers
Key: serviceDescriptions         Value: Installation, repair, and maintenance of water heaters
```

**File Uploads**:
```
Key: imageFiles                  Value: [FILE] - Select image file (can select multiple)
Key: imageFiles                  Value: [FILE] - Select another image file
```

**Note**: 
- For `logoFile` and `imageFiles`, change the field type from "Text" to "File" using the dropdown
- You can add multiple `serviceNames` and `serviceDescriptions` - they will be matched by order
- Maximum 10 images allowed

**Expected Response**:
```json
{
  "id": 1,
  "fullName": "John Doe",
  "companyName": "ABC Plumbing Services",
  "email": "john@abcplumbing.com",
  "logoFileUrl": "/api/client-journeys/logos/view/logo_1234567890.png",
  "servicesOrProducts": [
    {
      "id": 1,
      "name": "Pipe Installation",
      "description": "Professional installation of pipes..."
    },
    {
      "id": 2,
      "name": "Leak Repair",
      "description": "Quick and reliable repair..."
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

**Note**: A confirmation email will be sent automatically to the provided email address.

---

### 7.2 Get All Client Journeys

**Endpoint**: `GET /api/client-journeys`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys`

**Expected Response**:
```json
[
  {
    "id": 1,
    "fullName": "John Doe",
    "companyName": "ABC Plumbing Services",
    "email": "john@abcplumbing.com",
    "servicesOrProducts": [...],
    "optionalImages": [...]
  }
]
```

---

### 7.3 Get Client Journey by ID

**Endpoint**: `GET /api/client-journeys/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys/1`

**Expected Response**: Complete client journey object with all relationships

---

### 7.4 Update Client Journey

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
  "companyName": "XYZ Plumbing Services",
  "email": "jane@xyzplumbing.com",
  "businessLocation": "Swakopmund",
  "aboutInformation": "Updated business description",
  "businessPhone": "+264 61 987 6543",
  "businessEmail": "info@xyzplumbing.com",
  "primaryCta": "WhatsApp Us",
  "ctaLinkOrPhone": "+264 81 987 6543"
}
```

---

### 7.5 Delete Client Journey

**Endpoint**: `DELETE /api/client-journeys/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/client-journeys/1`

**Note**: This will also delete all associated services/products and optional images.

---

### 7.6 View Logo

**Endpoint**: `GET /api/client-journeys/logos/view/{fileName}`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys/logos/view/logo_1234567890.png`

**Expected Response**: Image file (binary)

---

### 7.7 View Image

**Endpoint**: `GET /api/client-journeys/images/view/{fileName}`

**Method**: GET  
**URL**: `http://localhost:8080/api/client-journeys/images/view/image1_1234567890.jpg`

**Expected Response**: Image file (binary)

---

## 8. Service/Product APIs

### 8.1 Create Service/Product

**Endpoint**: `POST /api/services-or-products`

**Method**: POST  
**URL**: `http://localhost:8080/api/services-or-products`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "name": "House Painting",
  "description": "Professional interior and exterior painting services for residential properties",
  "clientJourney": {
    "id": 1
  }
}
```

**More Sample Services**:
```json
{
  "name": "Interior Design",
  "description": "Modern interior design solutions tailored to your style and budget",
  "clientJourney": {
    "id": 1
  }
}
```

**Expected Response**:
```json
{
  "id": 1,
  "name": "House Painting",
  "description": "Professional interior and exterior painting services...",
  "createdAt": "2025-01-15T10:30:00"
}
```

---

### 8.2 Get All Services/Products

**Endpoint**: `GET /api/services-or-products`

**Method**: GET  
**URL**: `http://localhost:8080/api/services-or-products`

---

### 8.3 Get Service/Product by ID

**Endpoint**: `GET /api/services-or-products/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/services-or-products/1`

---

### 8.4 Get Services/Products by Client Journey ID

**Endpoint**: `GET /api/services-or-products/client-journey/{clientJourneyId}`

**Method**: GET  
**URL**: `http://localhost:8080/api/services-or-products/client-journey/1`

**Expected Response**:
```json
[
  {
    "id": 1,
    "name": "House Painting",
    "description": "Professional interior and exterior painting..."
  },
  {
    "id": 2,
    "name": "Interior Design",
    "description": "Modern interior design solutions..."
  }
]
```

---

### 8.5 Update Service/Product

**Endpoint**: `PUT /api/services-or-products/{id}`

**Method**: PUT  
**URL**: `http://localhost:8080/api/services-or-products/1`  
**Headers**: 
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "name": "Premium House Painting",
  "description": "Updated description with premium services"
}
```

---

### 8.6 Delete Service/Product

**Endpoint**: `DELETE /api/services-or-products/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/services-or-products/1`

---

## 9. Optional Image APIs

### 9.1 Upload Single Image (File Upload)

**Endpoint**: `POST /api/optional-images/upload`

**Method**: POST  
**URL**: `http://localhost:8080/api/optional-images/upload`

**Body** (form-data):
```
Key: file                    Value: [FILE] - Select image file
Key: clientJourneyId         Value: 1
```

**Steps in Postman**:
1. Select **Body** tab
2. Choose **form-data**
3. Add key `file` - Change type to "File", click "Select Files"
4. Add key `clientJourneyId` - Keep as "Text", enter `1`

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

### 9.2 Upload Multiple Images (File Upload)

**Endpoint**: `POST /api/optional-images/upload-multiple`

**Method**: POST  
**URL**: `http://localhost:8080/api/optional-images/upload-multiple`

**Body** (form-data):
```
Key: files                  Value: [FILE] - Select multiple image files (hold Ctrl/Cmd)
Key: clientJourneyId         Value: 1
```

**Expected Response**:
```json
[
  {
    "id": 1,
    "fileName": "image1_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image1_1234567890.jpg"
  },
  {
    "id": 2,
    "fileName": "image2_1234567890.jpg",
    "fileUrl": "/api/client-journeys/images/view/image2_1234567890.jpg"
  }
]
```

---

### 9.3 Create Optional Image (JSON - Alternative)

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

---

### 9.4 Get All Optional Images

**Endpoint**: `GET /api/optional-images`

**Method**: GET  
**URL**: `http://localhost:8080/api/optional-images`

---

### 9.5 Get Optional Image by ID

**Endpoint**: `GET /api/optional-images/{id}`

**Method**: GET  
**URL**: `http://localhost:8080/api/optional-images/1`

---

### 9.6 Get Optional Images by Client Journey ID

**Endpoint**: `GET /api/optional-images/client-journey/{clientJourneyId}`

**Method**: GET  
**URL**: `http://localhost:8080/api/optional-images/client-journey/1`

---

### 9.7 Update Optional Image

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

### 9.8 Delete Optional Image

**Endpoint**: `DELETE /api/optional-images/{id}`

**Method**: DELETE  
**URL**: `http://localhost:8080/api/optional-images/1`

---

## 10. Authentication

### 10.1 Login

**Endpoint**: `POST /api/users/login`

**Method**: POST  
**URL**: `http://localhost:8080/api/users/login?email=admin@simplyfound.com&password=admin123`

**Or use form-data**:
```
Key: email                    Value: admin@simplyfound.com
Key: password                 Value: admin123
```

**Expected Response**:
```
Login successful!
```

**Failed Response**:
```
Invalid email or password!
```

---

## Quick Testing Workflow

### Step 1: Setup Reference Data

1. **Create Industries** (at least 1):
   ```
   POST /api/industries
   {"industry": "Services"}
   ```

2. **Create Color Palettes** (at least 1):
   ```
   POST /api/color-palettes
   {"name": "Ocean Blue", "color1": "#1E3A8A", "color2": "#3B82F6", "color3": "#60A5FA", "color4": "#DBEAFE"}
   ```

3. **Create Layout Styles** (at least 1):
   ```
   POST /api/layout-styles
   {"style": "Clean & Minimal"}
   ```

4. **Create Opening Days** (at least 1):
   ```
   POST /api/opening-days
   {"openingDays": "Mon – Fri"}
   ```

### Step 2: Create Admin User

```
POST /api/users
{
  "email": "admin@simplyfound.com",
  "password": "admin123",
  "name": "Admin User",
  "role": "ADMIN"
}
```

### Step 3: Submit Client Journey Form

```
POST /api/client-journeys/submit
[Use form-data with all fields as shown in section 7.1]
```

### Step 4: Test Other Endpoints

- Get all client journeys
- Get client journey by ID
- Upload additional images
- View uploaded files
- Test CRUD operations

---

## Sample Data Summary

### Complete Form Submission Sample:

**Required Fields**:
- Full Name: `John Doe`
- Company Name: `ABC Plumbing Services`
- Email: `john@abcplumbing.com`
- Business Location: `Windhoek, Namibia`
- About Information: `We provide professional plumbing services...`
- Business Phone: `+264 61 123 4567`
- Business Email: `info@abcplumbing.com`
- Primary CTA: `Call Now`
- CTA Link/Phone: `+264 61 123 4567`

**Services** (Arrays):
- Service 1: `Pipe Installation` - `Professional installation of pipes...`
- Service 2: `Leak Repair` - `Quick and reliable repair...`
- Service 3: `Drain Cleaning` - `Thorough cleaning and unclogging...`

**Files**:
- Logo: Upload any image file
- Images: Upload 1-10 image files

---

## Common Issues & Solutions

### Issue: "Required request part 'fullName' is not present"
**Solution**: Use `form-data` in Postman, not `raw` or `x-www-form-urlencoded`

### Issue: "Industry not found"
**Solution**: Create the industry first using `POST /api/industries`

### Issue: "ClientJourney not found"
**Solution**: Create a client journey first using `POST /api/client-journeys/submit`

### Issue: Arrays not working
**Solution**: In Postman form-data, add multiple fields with the same key name

### Issue: File upload failed
**Solution**: 
- Check file size (max 650MB per file)
- Ensure file is an image (jpg, png, gif, etc.)
- Check upload directory permissions

### Issue: CORS error
**Solution**: Ensure backend is running and CORS is enabled (already configured)

---

## Testing Checklist

- [ ] Backend is running on `http://localhost:8080`
- [ ] Database is connected
- [ ] Create reference data (industries, color palettes, etc.)
- [ ] Create admin user
- [ ] Test form submission with all fields
- [ ] Test form submission with file uploads
- [ ] Test all GET endpoints
- [ ] Test all POST endpoints
- [ ] Test all PUT endpoints
- [ ] Test all DELETE endpoints
- [ ] Test file viewing endpoints
- [ ] Test authentication/login
- [ ] Verify email confirmation is sent

---

## Notes

1. **File Uploads**: Use `form-data` with file type selection in Postman
2. **Arrays**: Add multiple fields with the same key name for arrays
3. **Relationships**: Reference data must exist before using in client journey
4. **Email**: Confirmation email sent automatically after form submission
5. **Cascade Delete**: Deleting client journey deletes associated data

---

## Support

For issues:
- Check backend logs
- Verify database connection
- Check file upload paths and permissions
- Verify email configuration

