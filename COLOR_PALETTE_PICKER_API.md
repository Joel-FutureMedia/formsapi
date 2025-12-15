# Color Palette Picker API Documentation

This document explains the Color Palette API updates designed to support a color palette picker in the frontend.

---

## Overview

The Color Palette API has been enhanced to support a rich color palette picker interface. Users can now:
- Browse predefined color palettes
- Filter palettes by category
- View palettes in a specific order
- Create custom color palettes
- See descriptions and categories for each palette

---

## Model Updates

### New Fields Added to ColorPalette

1. **color5** (String, Optional)
   - Fifth color in the palette
   - Allows for 5-color palettes

2. **description** (String, Optional, max 500 chars)
   - Description of the palette
   - Helps users understand when to use the palette

3. **category** (String, Optional)
   - Category grouping (e.g., "Professional", "Nature", "Creative", "Luxury")
   - Enables filtering in the picker UI

4. **isPredefined** (Boolean, Default: false)
   - Indicates if this is a predefined/system palette
   - Useful for showing default options vs user-created palettes

5. **displayOrder** (Integer, Default: 0)
   - Controls the order in which palettes appear
   - Lower numbers appear first

---

## API Endpoints

### 1. Get All Color Palettes (Standard)

**Endpoint**: `GET /api/color-palettes`

**Description**: Returns all color palettes in default order.

**Response**:
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
    "description": "A calming blue palette perfect for professional websites",
    "category": "Professional",
    "isPredefined": true,
    "displayOrder": 1,
    "createdAt": "2025-01-15T10:00:00"
  }
]
```

---

### 2. Get Color Palettes for Picker (Recommended for Frontend)

**Endpoint**: `GET /api/color-palettes/picker`

**Description**: Returns all color palettes ordered by `displayOrder` (ascending) and then by `name` (ascending). This is the **recommended endpoint** for populating a color palette picker UI.

**Use Case**: Use this endpoint when loading the color palette picker component.

**Response**: Same structure as above, but ordered for picker display.

---

### 3. Get Predefined Color Palettes

**Endpoint**: `GET /api/color-palettes/predefined`

**Description**: Returns only predefined/system color palettes (where `isPredefined = true`).

**Use Case**: Show only default/system palettes, excluding user-created custom palettes.

**Response**: Array of predefined color palettes.

---

### 4. Get Color Palettes by Category

**Endpoint**: `GET /api/color-palettes/category/{category}`

**Description**: Returns color palettes filtered by a specific category.

**Use Case**: Implement category filtering in the picker UI (e.g., filter by "Professional", "Nature", etc.).

**Example Requests**:
- `GET /api/color-palettes/category/Professional`
- `GET /api/color-palettes/category/Nature`
- `GET /api/color-palettes/category/Creative`
- `GET /api/color-palettes/category/Luxury`

**Response**: Array of color palettes matching the category.

---

### 5. Get All Categories

**Endpoint**: `GET /api/color-palettes/categories`

**Description**: Returns a list of all unique category names.

**Use Case**: Build category filter dropdowns or tabs in the picker UI.

**Response**:
```json
[
  "Creative",
  "Luxury",
  "Nature",
  "Professional"
]
```

---

### 6. Create Color Palette

**Endpoint**: `POST /api/color-palettes`

**Body**:
```json
{
  "name": "Custom Palette",
  "color1": "#FF0000",
  "color2": "#00FF00",
  "color3": "#0000FF",
  "color4": "#FFFF00",
  "color5": "#FF00FF",
  "description": "My custom color palette",
  "category": "Custom",
  "isPredefined": false,
  "displayOrder": 100
}
```

**Note**: 
- `color5` is optional
- `description` is optional
- `category` is optional
- `isPredefined` defaults to `false` if not provided
- `displayOrder` defaults to `0` if not provided

---

## Frontend Integration Guide

### Recommended Implementation

1. **Load Palettes for Picker**:
   ```javascript
   GET /api/color-palettes/picker
   ```
   Use this to populate the picker with all available palettes in the correct order.

2. **Load Categories** (for filtering):
   ```javascript
   GET /api/color-palettes/categories
   ```
   Use this to build category filter buttons/tabs.

3. **Filter by Category** (when user selects a category):
   ```javascript
   GET /api/color-palettes/category/{category}
   ```
   Use this when user clicks on a category filter.

4. **Show Only Predefined** (optional):
   ```javascript
   GET /api/color-palettes/predefined
   ```
   Use this if you want to show only system palettes initially.

### Example Frontend Flow

```javascript
// 1. Load all palettes for picker
const palettes = await fetch('/api/color-palettes/picker').then(r => r.json());

// 2. Load categories for filter
const categories = await fetch('/api/color-palettes/categories').then(r => r.json());

// 3. When user selects a category
const filteredPalettes = await fetch(`/api/color-palettes/category/${category}`)
  .then(r => r.json());

// 4. When user selects a palette, use the ID
const selectedPaletteId = 1; // from picker selection
// Use this ID when submitting the form: colorPaletteId: selectedPaletteId
```

---

## Sample Data for Testing

### Predefined Professional Palettes

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

```json
{
  "name": "Corporate Grey",
  "color1": "#1F2937",
  "color2": "#4B5563",
  "color3": "#9CA3AF",
  "color4": "#E5E7EB",
  "color5": "#F9FAFB",
  "description": "Professional grey tones for corporate websites",
  "category": "Professional",
  "isPredefined": true,
  "displayOrder": 2
}
```

### Predefined Nature Palettes

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
  "displayOrder": 3
}
```

### Predefined Creative Palettes

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
  "displayOrder": 4
}
```

### Predefined Luxury Palettes

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
  "displayOrder": 5
}
```

---

## UI Recommendations

### Color Palette Picker Display

When displaying palettes in the picker UI:

1. **Show Color Swatches**: Display all 5 colors (or 4 if color5 is null) as swatches
2. **Show Name**: Display the palette name prominently
3. **Show Description**: Display description as tooltip or subtitle
4. **Show Category Badge**: Display category as a badge/tag
5. **Order**: Display palettes in the order returned by `/picker` endpoint

### Example Picker Card Structure

```
┌─────────────────────────────┐
│  Ocean Blue                 │
│  [Professional]             │
│                             │
│  [🔵] [🔵] [🔵] [🔵] [🔵]   │
│  #1E3A8A #3B82F6 ...        │
│                             │
│  A calming blue palette... │
└─────────────────────────────┘
```

---

## Migration Notes

### Existing Data

- Existing color palettes will have:
  - `color5`: `null`
  - `description`: `null`
  - `category`: `null`
  - `isPredefined`: `false`
  - `displayOrder`: `0`

### Updating Existing Palettes

You can update existing palettes to add the new fields:

```json
PUT /api/color-palettes/{id}
{
  "name": "Existing Palette",
  "color1": "#...",
  "color2": "#...",
  "color3": "#...",
  "color4": "#...",
  "color5": "#...",  // Add this
  "description": "Add description",  // Add this
  "category": "Professional",  // Add this
  "isPredefined": true,  // Update this
  "displayOrder": 1  // Update this
}
```

---

## Testing Checklist

- [ ] Create color palettes with all new fields
- [ ] Test `/api/color-palettes/picker` endpoint
- [ ] Test `/api/color-palettes/predefined` endpoint
- [ ] Test `/api/color-palettes/category/{category}` endpoint
- [ ] Test `/api/color-palettes/categories` endpoint
- [ ] Verify displayOrder sorting works correctly
- [ ] Test creating custom palettes (isPredefined: false)
- [ ] Test filtering by category in frontend
- [ ] Verify color palette selection works in form submission

---

## Notes

1. **Color Format**: All colors should be in hex format (e.g., `#1E3A8A`)
2. **Color Count**: Palettes can have 3-5 colors (color1-3 required, color4-5 optional)
3. **Display Order**: Lower numbers appear first. Use increments of 10 for easy reordering
4. **Categories**: Categories are case-sensitive. Use consistent naming
5. **Predefined vs Custom**: Use `isPredefined: true` for system palettes, `false` for user-created ones

