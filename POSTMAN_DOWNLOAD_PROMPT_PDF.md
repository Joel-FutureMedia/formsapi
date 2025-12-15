# How to Download Prompt PDF Using Postman

Step-by-step guide to download the website development prompt PDF file using Postman.

---

## Prerequisites

1. **Postman installed** (Desktop app or web version)
2. **Backend running** on `http://localhost:8080`
3. **Client Journey ID** (from a submitted form)

---

## Step-by-Step Instructions

### Step 1: Open Postman

Launch the Postman application on your computer.

---

### Step 2: Create New Request

1. Click the **"New"** button (top left)
2. Select **"HTTP Request"**
3. A new request tab will open

---

### Step 3: Set Request Method

1. In the request tab, you'll see a dropdown on the left
2. Select **GET** from the dropdown
   - Default is usually GET, so you might not need to change it

---

### Step 4: Enter the URL

1. In the URL field (next to GET), enter:
   ```
   http://localhost:8080/api/client-journeys/{id}/download-prompt
   ```

2. **Replace `{id}`** with the actual Client Journey ID

   **Example URLs**:
   ```
   http://localhost:8080/api/client-journeys/1/download-prompt
   http://localhost:8080/api/client-journeys/2/download-prompt
   http://localhost:8080/api/client-journeys/5/download-prompt
   ```

---

### Step 5: Find Client Journey ID (If You Don't Know It)

If you don't know the Client Journey ID, first get it:

#### Option A: Get All Client Journeys

1. Create a new GET request
2. URL: `http://localhost:8080/api/client-journeys`
3. Click **Send**
4. Look at the response - you'll see an array with all client journeys
5. Find the `id` field of the one you want

**Example Response**:
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

#### Option B: Get Specific Client Journey

1. Create a new GET request
2. URL: `http://localhost:8080/api/client-journeys/1` (replace 1 with any ID)
3. Click **Send**
4. Check the `id` field in the response

---

### Step 6: Send Request and Download PDF

#### Method 1: Send and Download (Recommended)

1. Make sure your GET request is set up correctly:
   - Method: **GET**
   - URL: `http://localhost:8080/api/client-journeys/1/download-prompt`

2. Click the **"Send"** button

3. **Wait for response** - Postman will show the PDF in the response body

4. **Download the file**:
   - Look for the **"Save Response"** button (next to Send)
   - Click the dropdown arrow next to "Save Response"
   - Select **"Save to a file"**
   - Choose where to save the file
   - The file will be named: `website-prompt-{id}.pdf`

#### Method 2: Send and Download Button

1. Set up your GET request as above

2. Click the **dropdown arrow** next to the "Send" button

3. Select **"Send and Download"**

4. Postman will automatically:
   - Send the request
   - Download the PDF file
   - Save it to your Downloads folder (or prompt you to choose location)

5. The file will be saved as: `website-prompt-{id}.pdf`

---

## Visual Guide

### Postman Request Setup:

```
┌─────────────────────────────────────────────────────────┐
│  GET  │  http://localhost:8080/api/client-journeys/1/   │
│       │  download-prompt                                │
│                                                         │
│  [Send] [Send and Download ▼]                          │
└─────────────────────────────────────────────────────────┘
```

### After Sending:

```
┌─────────────────────────────────────────────────────────┐
│  Status: 200 OK                                         │
│  Time: 234ms                                            │
│  Size: 156 KB                                           │
│                                                         │
│  Body | Preview | Headers | Cookies                     │
│                                                         │
│  [PDF content displayed here]                           │
│                                                         │
│  [Save Response ▼] → [Save to a file]                  │
└─────────────────────────────────────────────────────────┘
```

---

## Complete Example

### Example 1: Download Prompt for Client Journey ID 1

1. **Method**: GET
2. **URL**: `http://localhost:8080/api/client-journeys/1/download-prompt`
3. **Click**: "Send and Download"
4. **Result**: File saved as `website-prompt-1.pdf`

### Example 2: Download Prompt for Client Journey ID 5

1. **Method**: GET
2. **URL**: `http://localhost:8080/api/client-journeys/5/download-prompt`
3. **Click**: "Send and Download"
4. **Result**: File saved as `website-prompt-5.pdf`

---

## Response Details

### Success Response (200 OK):

- **Status**: `200 OK`
- **Content-Type**: `application/pdf`
- **Body**: PDF file content (binary)
- **Size**: Typically 50-200 KB

### What You'll See in Postman:

- **Status Code**: `200 OK` (green)
- **Time**: Response time in milliseconds
- **Size**: File size in KB
- **Body Tab**: PDF preview (if supported) or binary data
- **Headers Tab**: Response headers including `Content-Type: application/pdf`

---

## Troubleshooting

### Issue: "404 Not Found"

**Error Message**:
```
Status: 404 Not Found
{
  "message": "ClientJourney not found"
}
```

**Solution**:
1. Verify the Client Journey ID exists
2. Use `GET /api/client-journeys` to list all journeys
3. Check the `id` field in the response
4. Use the correct ID in the URL

---

### Issue: "500 Internal Server Error"

**Error Message**:
```
Status: 500 Internal Server Error
{
  "message": "Failed to generate prompt PDF"
}
```

**Solution**:
1. Check backend logs for detailed error
2. Verify the Client Journey has all required data
3. Ensure iText7 dependencies are installed
4. Check if colour palette or other relationships exist

---

### Issue: PDF Doesn't Download

**Possible Causes**:
1. **Browser blocking downloads**: Check browser settings
2. **Postman settings**: Check Postman download settings
3. **File already exists**: Check if file with same name exists

**Solution**:
1. Use "Send and Download" instead of "Send"
2. Check Postman → Settings → General → "Always ask where to save files"
3. Manually save using "Save Response" → "Save to a file"

---

### Issue: PDF File is Corrupted or Empty

**Solution**:
1. Check backend logs for PDF generation errors
2. Verify all Client Journey data is complete
3. Try regenerating the PDF
4. Check file size - if it's 0 bytes, generation failed

---

### Issue: Can't See PDF Preview

**Solution**:
- This is normal - PDFs may not preview in Postman
- Use "Send and Download" to save the file
- Open the saved PDF file with a PDF reader

---

## Quick Checklist

Before downloading:

- [ ] Postman is open and running
- [ ] Backend is running on `http://localhost:8080`
- [ ] You know the Client Journey ID
- [ ] Request method is set to **GET**
- [ ] URL is correct: `http://localhost:8080/api/client-journeys/{id}/download-prompt`
- [ ] You've replaced `{id}` with actual number

---

## Tips & Best Practices

1. **Use "Send and Download"**: This is the easiest method - it automatically saves the file

2. **Save to Specific Folder**: 
   - Use "Save Response" → "Save to a file" to choose location
   - Create a folder like "Website Prompts" to organize files

3. **Rename Files**: 
   - After downloading, you can rename the file
   - Include company name: `website-prompt-ABC-Company.pdf`

4. **Verify Download**: 
   - Check file size (should be > 0 bytes)
   - Open PDF to verify content
   - Check that all sections are included

5. **Test First**: 
   - Use a known Client Journey ID
   - Verify the endpoint works
   - Then download for production use

---

## Alternative: Using Postman Collection

### Create a Collection:

1. Click **"New"** → **"Collection"**
2. Name it: "SimplyFound API"
3. Add request: Right-click collection → "Add Request"
4. Name: "Download Prompt PDF"
5. Set method: GET
6. Set URL: `http://localhost:8080/api/client-journeys/{{clientJourneyId}}/download-prompt`
7. Add variable: `clientJourneyId` = 1 (default)

### Use Collection Variable:

1. Edit the request URL
2. Use: `{{clientJourneyId}}` instead of hardcoded ID
3. Set variable value in collection variables
4. Change value when needed without editing URL

---

## Summary

**Quick Steps**:
1. Open Postman
2. Create GET request
3. URL: `http://localhost:8080/api/client-journeys/{id}/download-prompt`
4. Click "Send and Download"
5. PDF file saved!

**That's it!** The PDF will be downloaded to your default download location or where you choose to save it.

