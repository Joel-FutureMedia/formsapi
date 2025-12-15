# How to Download Generated Prompt PDF

This guide explains how to download the website development prompt PDF file for a client journey submission.

---

## Overview

After a client submits their form, you can generate and download a comprehensive PDF prompt file that contains all the information needed to build their website. This PDF includes:

- Client information
- Colour palette details
- Landing page structure requirements
- Copywriting guidelines
- Design principles
- Technical requirements
- Content requirements
- And much more...

---

## Prerequisites

1. **Client Journey Must Exist**
   - A client must have submitted the form first
   - You need the Client Journey ID

2. **Backend Must Be Running**
   - Ensure the Spring Boot backend is running on `http://localhost:8080`

---

## Method 1: Using Postman

### Step-by-Step Instructions:

1. **Open Postman**

2. **Create a New Request**
   - Click "New" → "HTTP Request"

3. **Set Request Method**
   - Select **GET** from the dropdown

4. **Enter the URL**
   ```
   http://localhost:8080/api/client-journeys/{id}/download-prompt
   ```
   Replace `{id}` with the actual Client Journey ID.

   **Example**:
   ```
   http://localhost:8080/api/client-journeys/1/download-prompt
   ```

5. **Send the Request**
   - Click the "Send" button

6. **Download the PDF**
   - Postman will show the PDF in the response
   - Click "Send and Download" (icon next to Send button) to save the file
   - Or right-click on the response → "Save Response" → "Save to File"
   - The file will be named: `website-prompt-{id}.pdf`

---

## Method 2: Using cURL (Command Line)

### Basic Command:

```bash
curl -X GET http://localhost:8080/api/client-journeys/{id}/download-prompt --output website-prompt.pdf
```

**Example**:
```bash
curl -X GET http://localhost:8080/api/client-journeys/1/download-prompt --output website-prompt-1.pdf
```

### With Headers (Optional):

```bash
curl -X GET \
  http://localhost:8080/api/client-journeys/1/download-prompt \
  -H "Accept: application/pdf" \
  --output website-prompt-1.pdf
```

---

## Method 3: Using Browser

### Direct Browser Access:

1. **Open your web browser**

2. **Navigate to the URL**:
   ```
   http://localhost:8080/api/client-journeys/1/download-prompt
   ```
   (Replace `1` with the actual Client Journey ID)

3. **PDF Will Download Automatically**
   - The browser will prompt you to save the file
   - Save it with the name: `website-prompt-{id}.pdf`

---

## Method 4: Using JavaScript/Frontend

### Fetch API Example:

```javascript
async function downloadPromptPdf(clientJourneyId) {
    try {
        const response = await fetch(
            `http://localhost:8080/api/client-journeys/${clientJourneyId}/download-prompt`
        );
        
        if (!response.ok) {
            throw new Error('Failed to download PDF');
        }
        
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `website-prompt-${clientJourneyId}.pdf`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
    } catch (error) {
        console.error('Error downloading PDF:', error);
    }
}

// Usage
downloadPromptPdf(1);
```

### Axios Example:

```javascript
import axios from 'axios';

async function downloadPromptPdf(clientJourneyId) {
    try {
        const response = await axios.get(
            `http://localhost:8080/api/client-journeys/${clientJourneyId}/download-prompt`,
            {
                responseType: 'blob'
            }
        );
        
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `website-prompt-${clientJourneyId}.pdf`);
        document.body.appendChild(link);
        link.click();
        link.remove();
    } catch (error) {
        console.error('Error downloading PDF:', error);
    }
}

// Usage
downloadPromptPdf(1);
```

---

## Method 5: Using Admin Panel (Frontend Integration)

### React Component Example:

```jsx
import React, { useState } from 'react';

function DownloadPromptButton({ clientJourneyId }) {
    const [downloading, setDownloading] = useState(false);

    const handleDownload = async () => {
        setDownloading(true);
        try {
            const response = await fetch(
                `http://localhost:8080/api/client-journeys/${clientJourneyId}/download-prompt`
            );
            
            if (!response.ok) {
                throw new Error('Failed to download PDF');
            }
            
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `website-prompt-${clientJourneyId}.pdf`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(a);
            
            alert('PDF downloaded successfully!');
        } catch (error) {
            console.error('Error:', error);
            alert('Failed to download PDF. Please try again.');
        } finally {
            setDownloading(false);
        }
    };

    return (
        <button 
            onClick={handleDownload} 
            disabled={downloading}
            className="download-btn"
        >
            {downloading ? 'Downloading...' : 'Download Prompt PDF'}
        </button>
    );
}

export default DownloadPromptButton;
```

---

## Finding the Client Journey ID

### Option 1: Get All Client Journeys

**Endpoint**: `GET /api/client-journeys`

**Response**:
```json
[
  {
    "id": 1,
    "companyName": "ABC Company",
    "email": "john@example.com",
    ...
  },
  {
    "id": 2,
    "companyName": "XYZ Company",
    "email": "jane@example.com",
    ...
  }
]
```

### Option 2: Get Specific Client Journey

**Endpoint**: `GET /api/client-journeys/{id}`

**Response**:
```json
{
  "id": 1,
  "companyName": "ABC Company",
  "email": "john@example.com",
  ...
}
```

---

## Response Details

### Success Response:

- **Status Code**: `200 OK`
- **Content-Type**: `application/pdf`
- **Content-Disposition**: `attachment; filename="website-prompt-{id}.pdf"`
- **Body**: PDF file bytes

### Error Responses:

#### 404 Not Found:
```json
{
  "timestamp": "2025-01-15T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "ClientJourney not found",
  "path": "/api/client-journeys/999/download-prompt"
}
```

**Solution**: Verify the Client Journey ID exists.

#### 500 Internal Server Error:
```json
{
  "timestamp": "2025-01-15T10:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Failed to generate prompt PDF",
  "path": "/api/client-journeys/1/download-prompt"
}
```

**Solution**: 
- Check backend logs for detailed error
- Ensure all required data is present in the Client Journey
- Verify PDF generation dependencies are working

---

## Complete Workflow Example

### Step 1: Submit Form (if not done already)

```bash
POST http://localhost:8080/api/client-journeys/submit
[Form data with all fields]
```

**Response**: Returns Client Journey object with `id: 1`

### Step 2: Download Prompt PDF

```bash
GET http://localhost:8080/api/client-journeys/1/download-prompt
```

**Result**: PDF file downloaded as `website-prompt-1.pdf`

---

## Testing Checklist

- [ ] Backend is running on port 8080
- [ ] Client Journey exists in database
- [ ] Client Journey ID is correct
- [ ] PDF downloads successfully
- [ ] PDF file opens correctly
- [ ] PDF contains all expected sections
- [ ] PDF includes client information
- [ ] PDF includes colour palette (if selected)
- [ ] PDF includes all requirements from landingpage.md
- [ ] PDF includes all prompts from promp.md

---

## Troubleshooting

### Issue: "ClientJourney not found"
**Solution**: 
- Verify the Client Journey ID exists
- Check database for the correct ID
- Use `GET /api/client-journeys` to list all journeys

### Issue: PDF doesn't download
**Solution**:
- Check browser download settings
- Try a different browser
- Use Postman's "Send and Download" option
- Check browser console for errors

### Issue: PDF is empty or corrupted
**Solution**:
- Check backend logs for errors
- Verify all required data is present
- Ensure iText7 dependencies are properly installed
- Try regenerating the PDF

### Issue: CORS error (if using frontend)
**Solution**:
- CORS is already enabled in the controller
- Verify the backend URL is correct
- Check browser console for specific CORS error

---

## File Naming Convention

The downloaded PDF will be named:
```
website-prompt-{clientJourneyId}.pdf
```

**Examples**:
- `website-prompt-1.pdf`
- `website-prompt-2.pdf`
- `website-prompt-123.pdf`

---

## Notes

1. **PDF Generation**: The PDF is generated on-demand when you request it
2. **File Size**: PDF size varies based on content (typically 50-200 KB)
3. **Content**: PDF includes all information from:
   - Client Journey form submission
   - Landing page requirements (landingpage.md)
   - Copywriting prompts (promp.md)
   - Technical requirements (project.md)
4. **No Storage**: PDFs are not stored on the server - they're generated each time
5. **Idempotent**: You can download the same PDF multiple times

---

## Quick Reference

**Endpoint**: 
```
GET /api/client-journeys/{id}/download-prompt
```

**Base URL**: 
```
http://localhost:8080
```

**Full URL Example**: 
```
http://localhost:8080/api/client-journeys/1/download-prompt
```

**Response**: PDF file (application/pdf)

**Filename**: `website-prompt-{id}.pdf`

---

## Support

If you encounter any issues:

1. Check backend logs for detailed error messages
2. Verify the Client Journey exists and has all required data
3. Ensure backend dependencies (iText7) are properly installed
4. Test with Postman first to isolate frontend vs backend issues

