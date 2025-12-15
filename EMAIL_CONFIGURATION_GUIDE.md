# Email Configuration Troubleshooting Guide

## Issue: Gmail Authentication Failed

If you're getting `Authentication failed` or `Username and Password not accepted` errors, follow these steps:

---

## Solution 1: Verify Gmail App Password

Gmail requires an **App Password** (not your regular Gmail password) when 2-Step Verification is enabled.

### Steps to Generate Gmail App Password:

1. **Go to Google Account Settings**
   - Visit: https://myaccount.google.com/
   - Sign in with your Gmail account

2. **Enable 2-Step Verification** (if not already enabled)
   - Go to Security → 2-Step Verification
   - Follow the setup process

3. **Generate App Password**
   - Go to Security → 2-Step Verification
   - Scroll down to "App passwords"
   - Click "App passwords"
   - Select "Mail" as the app
   - Select "Other (Custom name)" as device
   - Enter "SimplyFound Backend" as the name
   - Click "Generate"

4. **Copy the App Password**
   - You'll get a 16-character password (with spaces)
   - Example: `onwd ukvd gein wjf`
   - **Important**: You can use it WITH or WITHOUT spaces

5. **Update application.properties**
   ```properties
   spring.mail.password=onwd ukvd gein wjf
   ```
   OR (without spaces):
   ```properties
   spring.mail.password=onwdukvdgeinwjf
   ```

---

## Solution 2: Remove Spaces from App Password

Sometimes Gmail App Passwords work better without spaces. Try removing all spaces:

**Before**: `onwd ukvd gein wjf`  
**After**: `onwdukvdgeinwjf`

Update in `application.properties`:
```properties
spring.mail.password=onwdukvdgeinwjf
```

---

## Solution 3: Verify Email Configuration

Make sure your `application.properties` has the correct configuration:

```properties
# Email Configuration (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
```

**Important Notes**:
- `spring.mail.username` should be your **full Gmail address** (e.g., `joelkalimbwe3@gmail.com`)
- `spring.mail.password` should be your **App Password** (16 characters)
- Make sure there are no extra spaces or quotes around the password

---

## Solution 4: Check Account Security Settings

1. **Allow Less Secure Apps** (if 2FA is not enabled)
   - Go to: https://myaccount.google.com/lesssecureapps
   - Turn ON "Allow less secure apps"
   - **Note**: This is deprecated and not recommended. Use App Password instead.

2. **Check if Account is Locked**
   - Try logging into Gmail in a browser
   - If account is locked, unlock it first

---

## Solution 5: Test Email Configuration

After updating the configuration, restart your Spring Boot application and test:

1. **Submit a form** via Postman
2. **Check the logs** for email sending status
3. **Check the recipient's inbox** (and spam folder)

---

## Current Fix Applied

The code has been updated to:

1. **Not fail form submission if email fails**
   - Form will still be saved even if email sending fails
   - Email errors are logged but don't stop the request

2. **Better error logging**
   - Email errors are logged with details
   - You can check logs to see the exact error

3. **Improved email configuration**
   - Added timeout settings
   - Added required TLS settings

---

## Testing Email Configuration

### Test Email Sending:

1. **Submit a form**:
   ```
   POST http://localhost:8080/api/client-journeys/submit
   ```

2. **Check application logs**:
   - Look for: `Confirmation email sent successfully to: ...`
   - Or: `Failed to send confirmation email to: ...`

3. **Check recipient email**:
   - Check inbox and spam folder
   - Email should arrive within a few seconds

---

## Common Error Messages and Solutions

### Error: "Authentication failed"
**Solution**: 
- Verify App Password is correct
- Try removing spaces from App Password
- Regenerate App Password if needed

### Error: "Username and Password not accepted"
**Solution**:
- Make sure you're using App Password, not regular password
- Verify 2-Step Verification is enabled
- Check username is full email address

### Error: "Connection timeout"
**Solution**:
- Check internet connection
- Verify firewall isn't blocking port 587
- Try port 465 with SSL instead

### Error: "535-5.7.8 Username and Password not accepted"
**Solution**:
- This is a Gmail-specific error
- Regenerate App Password
- Make sure 2-Step Verification is enabled
- Try removing spaces from password

---

## Alternative: Use Port 465 with SSL

If port 587 doesn't work, try port 465 with SSL:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=465
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.ssl.required=true
```

---

## Important Notes

1. **App Passwords are required** when 2-Step Verification is enabled
2. **Regular passwords won't work** with Gmail SMTP
3. **Form submission will succeed** even if email fails (by design)
4. **Check logs** to see email sending status
5. **Email errors are logged** but don't prevent form submission

---

## Still Having Issues?

1. **Double-check App Password**:
   - Go to Google Account → Security → App passwords
   - Verify the password matches exactly

2. **Try regenerating App Password**:
   - Delete old App Password
   - Generate a new one
   - Update application.properties

3. **Check Gmail account status**:
   - Make sure account is active
   - Check for any security alerts
   - Verify account isn't locked

4. **Test with a different email service**:
   - Consider using SendGrid, Mailgun, or AWS SES
   - These services are more reliable for production

---

## Current Status

✅ **Form submission will work** even if email fails  
✅ **Email errors are logged** for debugging  
✅ **Better error handling** implemented  
⚠️ **Email configuration** needs to be verified with correct App Password

