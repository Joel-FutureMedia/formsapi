package com.example.simplyfound.services;

import com.example.simplyfound.models.ClientJourney;
import com.example.simplyfound.repositories.ClientJourneyRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PromptPdfService {
    
    @Autowired
    private ClientJourneyRepository clientJourneyRepository;

    public byte[] generatePromptPdf(Long clientJourneyId) {
        ClientJourney clientJourney = clientJourneyRepository.findById(clientJourneyId)
                .orElseThrow(() -> new RuntimeException("ClientJourney not found"));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            Paragraph title = new Paragraph("WEBSITE DEVELOPMENT PROMPT")
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Complete Form Data Summary Section (ALL FORM INFORMATION)
            addSectionHeader(document, "COMPLETE FORM DATA SUMMARY");
            addCompleteFormData(document, clientJourney);

            // Client Information Section
            addSectionHeader(document, "CLIENT INFORMATION");
            addClientInfo(document, clientJourney);

            // Color Section
            addSectionHeader(document, "COLOUR SELECTION");
            document.add(new Paragraph("Primary Colour: " + (clientJourney.getPrimaryColor() != null ? clientJourney.getPrimaryColor() : "Not selected")));
            document.add(new Paragraph("Secondary Colour: " + (clientJourney.getSecondaryColor() != null ? clientJourney.getSecondaryColor() : "Not selected")));
            document.add(new Paragraph("\n"));

            // Layout Style Section
            if (clientJourney.getLayoutStyle() != null) {
                addSectionHeader(document, "LAYOUT STYLE");
                addParagraph(document, clientJourney.getLayoutStyle().getStyle());
            }

            // Landing Page Structure Section
            addSectionHeader(document, "LANDING PAGE STRUCTURE & DESIGN REQUIREMENTS");
            addLandingPageStructure(document, clientJourney);

            // Copywriting Guidelines Section
            addSectionHeader(document, "COPYWRITING GUIDELINES");
            addCopywritingGuidelines(document, clientJourney);

            // Design Principles Section
            addSectionHeader(document, "DESIGN PRINCIPLES & BEST PRACTICES");
            addDesignPrinciples(document);

            // Website Pages & Navigation Section
            addSectionHeader(document, "WEBSITE PAGES & NAVIGATION");
            addPagesAndNavigation(document, clientJourney);

            // Technical Requirements Section
            addSectionHeader(document, "TECHNICAL REQUIREMENTS");
            addTechnicalRequirements(document);

            // Content Requirements Section
            addSectionHeader(document, "CONTENT REQUIREMENTS");
            addContentRequirements(document, clientJourney);

            // Services/Products Section (Detailed)
            addSectionHeader(document, "SERVICES & PRODUCTS DETAILED INFORMATION");
            addServicesProductsDetailed(document, clientJourney);
            
            // Optional Images Section (Detailed)
            addSectionHeader(document, "OPTIONAL IMAGES PROVIDED");
            addOptionalImagesDetailed(document, clientJourney);
            
            // Image Generation Section (only if no images uploaded)
            if (clientJourney.getOptionalImages() == null || clientJourney.getOptionalImages().isEmpty()) {
                addSectionHeader(document, "AI IMAGE GENERATION REQUIREMENTS");
                addImageGenerationRequirements(document, clientJourney);
            }

            // Social Proof & Trust Builders Section
            addSectionHeader(document, "SOCIAL PROOF & TRUST BUILDERS");
            addSocialProof(document, clientJourney);

            // Call-to-Action Requirements Section
            addSectionHeader(document, "CALL-TO-ACTION REQUIREMENTS");
            addCtaRequirements(document, clientJourney);

            // Footer Requirements Section
            addSectionHeader(document, "FOOTER REQUIREMENTS");
            addFooterRequirements(document, clientJourney);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate prompt PDF", e);
        }
    }

    private void addSectionHeader(Document document, String text) {
        Paragraph header = new Paragraph(text)
                .setFontSize(16)
                .setBold()
                .setMarginTop(20)
                .setMarginBottom(10)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setPadding(5);
        document.add(header);
    }

    private void addParagraph(Document document, String text) {
        if (text != null && !text.trim().isEmpty()) {
            document.add(new Paragraph(text).setMarginBottom(5));
        }
    }

    private void addCompleteFormData(Document document, ClientJourney cj) {
        document.add(new Paragraph("This section contains ALL information submitted by the client in the form.").setBold());
        document.add(new Paragraph("\nSTEP 1 - BASIC INFORMATION:"));
        document.add(new Paragraph("  • Full Name: " + cj.getFullName()));
        document.add(new Paragraph("  • Company Name: " + cj.getCompanyName()));
        document.add(new Paragraph("  • Email: " + cj.getEmail()));
        document.add(new Paragraph("  • Phone Number: " + (cj.getPhoneNumber() != null ? cj.getPhoneNumber() : "Not provided")));
        document.add(new Paragraph("  • Business Location: " + cj.getBusinessLocation()));
        
        document.add(new Paragraph("\nSTEP 2 - BUSINESS IDENTITY:"));
        document.add(new Paragraph("  • About Information: " + cj.getAboutInformation()));
        document.add(new Paragraph("  • Industry: " + (cj.getIndustry() != null ? cj.getIndustry().getIndustry() : "Not selected")));
        document.add(new Paragraph("  • Logo File: " + (cj.getLogoFileName() != null ? cj.getLogoFileName() : "Not uploaded")));
        if (cj.getLogoFileUrl() != null) {
            document.add(new Paragraph("  • Logo URL: " + cj.getLogoFileUrl()));
        }
        if (cj.getLogoFileType() != null) {
            document.add(new Paragraph("  • Logo File Type: " + cj.getLogoFileType()));
        }
        
        document.add(new Paragraph("\nSTEP 3 - CONTACT INFORMATION:"));
        document.add(new Paragraph("  • Business Phone: " + cj.getBusinessPhone()));
        document.add(new Paragraph("  • Business Email: " + cj.getBusinessEmail()));
        document.add(new Paragraph("  • Opening Days: " + (cj.getOpeningDays() != null ? cj.getOpeningDays().getOpeningDays() : "Not selected")));
        document.add(new Paragraph("  • Custom Hours: " + (cj.getCustomHours() != null ? cj.getCustomHours() : "Not provided")));
        
        document.add(new Paragraph("\nSTEP 4 - SERVICES & PRODUCTS:"));
        if (cj.getServicesOrProducts() != null && !cj.getServicesOrProducts().isEmpty()) {
            document.add(new Paragraph("  • Total Services/Products: " + cj.getServicesOrProducts().size()));
            int index = 1;
            for (com.example.simplyfound.models.ServiceOrProduct service : cj.getServicesOrProducts()) {
                document.add(new Paragraph("    " + index + ". " + service.getName()));
                document.add(new Paragraph("       Description: " + service.getDescription()));
                index++;
            }
        } else {
            document.add(new Paragraph("  • No services or products provided"));
        }
        
        document.add(new Paragraph("\nSTEP 5 - VISUAL STYLE:"));
        document.add(new Paragraph("  • Primary Colour: " + (cj.getPrimaryColor() != null ? cj.getPrimaryColor() : "Not selected")));
        document.add(new Paragraph("  • Secondary Colour: " + (cj.getSecondaryColor() != null ? cj.getSecondaryColor() : "Not selected")));
        document.add(new Paragraph("  • Layout Style: " + (cj.getLayoutStyle() != null ? cj.getLayoutStyle().getStyle() : "Not selected")));
        document.add(new Paragraph("  • Optional Images: " + (cj.getOptionalImages() != null ? cj.getOptionalImages().size() + " images" : "0 images")));
        
        document.add(new Paragraph("\nSTEP 6 - SOCIAL LINKS & TRUST BUILDERS:"));
        document.add(new Paragraph("  • Facebook URL: " + (cj.getFacebookUrl() != null ? cj.getFacebookUrl() : "Not provided")));
        document.add(new Paragraph("  • Instagram URL: " + (cj.getInstagramUrl() != null ? cj.getInstagramUrl() : "Not provided")));
        document.add(new Paragraph("  • Google Business URL: " + (cj.getGoogleBusinessUrl() != null ? cj.getGoogleBusinessUrl() : "Not provided")));
        document.add(new Paragraph("  • TikTok URL: " + (cj.getTiktokUrl() != null ? cj.getTiktokUrl() : "Not provided")));
        document.add(new Paragraph("  • Testimonials: " + (cj.getTestimonials() != null && !cj.getTestimonials().trim().isEmpty() ? cj.getTestimonials() : "Not provided")));
        
        document.add(new Paragraph("\nSTEP 7 - CALLS TO ACTION:"));
        document.add(new Paragraph("  • Primary CTA: " + cj.getPrimaryCta()));
        document.add(new Paragraph("  • CTA Link/Phone: " + cj.getCtaLinkOrPhone()));
        document.add(new Paragraph("  • Secondary Action: " + (cj.getSecondaryAction() != null ? cj.getSecondaryAction() : "Not provided")));
        
        document.add(new Paragraph("\n"));
    }

    private void addClientInfo(Document document, ClientJourney cj) {
        document.add(new Paragraph("Company Name: " + cj.getCompanyName()).setBold());
        document.add(new Paragraph("Full Name: " + cj.getFullName()));
        document.add(new Paragraph("Email: " + cj.getEmail()));
        if (cj.getPhoneNumber() != null) {
            document.add(new Paragraph("Phone: " + cj.getPhoneNumber()));
        }
        document.add(new Paragraph("Business Location: " + cj.getBusinessLocation()));
        document.add(new Paragraph("Business Phone: " + cj.getBusinessPhone()));
        document.add(new Paragraph("Business Email: " + cj.getBusinessEmail()));
        if (cj.getIndustry() != null) {
            document.add(new Paragraph("Industry: " + cj.getIndustry().getIndustry()));
        }
        document.add(new Paragraph("About: " + cj.getAboutInformation()));
        document.add(new Paragraph("\n"));
    }

    private void addColorPalette(Document document, com.example.simplyfound.models.ColorPalette palette) {
        document.add(new Paragraph("Palette Name: " + palette.getName()).setBold());
        if (palette.getDescription() != null) {
            document.add(new Paragraph("Description: " + palette.getDescription()));
        }
        document.add(new Paragraph("Primary Colours:"));
        document.add(new Paragraph("  • Colour 1: " + palette.getColor1()));
        document.add(new Paragraph("  • Colour 2: " + palette.getColor2()));
        document.add(new Paragraph("  • Colour 3: " + palette.getColor3()));
        if (palette.getColor4() != null) {
            document.add(new Paragraph("  • Colour 4: " + palette.getColor4()));
        }
        if (palette.getColor5() != null) {
            document.add(new Paragraph("  • Colour 5: " + palette.getColor5()));
        }
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("IMPORTANT: Use these colours consistently throughout the website. Apply them to:" +
                "\n• Headers and navigation" +
                "\n• Call-to-action buttons" +
                "\n• Links and hover states" +
                "\n• Accent elements" +
                "\n• Footer background"));
        document.add(new Paragraph("\n"));
    }

    private void addLandingPageStructure(Document document, ClientJourney cj) {
        document.add(new Paragraph("The landing page MUST follow this structure:"));
        document.add(new Paragraph("\n1. HERO SECTION (Above the Fold)"));
        document.add(new Paragraph("   • Clear value proposition (benefit-first, not feature-first)"));
        document.add(new Paragraph("   • Relevant supporting image (must reinforce value prop)"));
        document.add(new Paragraph("   • Primary CTA (soft CTA preferred: 'See How It Works', 'Get My Free Quote', 'Get Instant Access')"));
        document.add(new Paragraph("   • Immediate social proof (strongest, most relevant)"));
        document.add(new Paragraph("   • Visitor must understand in 5 seconds: What this is, Who it's for, What's in it for me, What to do next"));
        
        document.add(new Paragraph("\n   HERO SECTION BACKGROUND ANIMATION REQUIREMENTS:"));
        document.add(new Paragraph("   • Design a hero section background animation that visually represents what the business does."));
        document.add(new Paragraph("   • Create a subtle hero background animation that visually represents how this business works, using abstract motion tied directly to the product's core function."));
        document.add(new Paragraph("   • The animation must be designed based on the products or services that the company provides."));
        document.add(new Paragraph("   • The animation should loop smoothly, feel premium, not distract from the headline, and communicate the product's value instantly."));
        document.add(new Paragraph("   • This animation is for the hero section on the landing page and should work on all websites that are built."));
        document.add(new Paragraph("   • Consider the business type: " + (cj.getIndustry() != null ? cj.getIndustry().getIndustry() : "General business")));
        document.add(new Paragraph("   • Consider what the business does: " + cj.getAboutInformation()));
        if (cj.getServicesOrProducts() != null && !cj.getServicesOrProducts().isEmpty()) {
            document.add(new Paragraph("   • Consider the services/products offered:"));
            for (com.example.simplyfound.models.ServiceOrProduct service : cj.getServicesOrProducts()) {
                document.add(new Paragraph("     - " + service.getName() + ": " + service.getDescription()));
            }
        }
        document.add(new Paragraph("   • The animation should be subtle, professional, and enhance the user experience without being distracting."));
        document.add(new Paragraph("   • Use CSS animations, SVG animations, or canvas-based animations as appropriate."));
        document.add(new Paragraph("   • Ensure the animation is performant and doesn't impact page load times."));
        document.add(new Paragraph("   • The animation should be responsive and work well on all screen sizes."));
        
        document.add(new Paragraph("\n2. EMOTIONAL BENEFITS GRID"));
        document.add(new Paragraph("   • Use icons to represent each benefit"));
        document.add(new Paragraph("   • Focus on transformation, not features"));
        document.add(new Paragraph("   • Show before/after or problem/solution"));
        
        document.add(new Paragraph("\n3. TRANSFORMATION SECTION"));
        document.add(new Paragraph("   • Before/after visual representation"));
        document.add(new Paragraph("   • Problem → Solution narrative"));
        document.add(new Paragraph("   • Emotion → Result journey"));
        
        document.add(new Paragraph("\n4. SOCIAL PROOF SECTION"));
        document.add(new Paragraph("   • Quantified claims (numbers over words)"));
        document.add(new Paragraph("   • Testimonials matched to pain points"));
        document.add(new Paragraph("   • Transformation stories"));
        document.add(new Paragraph("   • Real customer success images"));
        
        document.add(new Paragraph("\n5. WHAT YOU OFFER SECTION"));
        document.add(new Paragraph("   • Simple packages (2-3 max to reduce decision fatigue)"));
        document.add(new Paragraph("   • Clear pricing (if applicable)"));
        document.add(new Paragraph("   • Feature comparison"));
        
        document.add(new Paragraph("\n6. HOW IT WORKS SECTION"));
        document.add(new Paragraph("   • 3-step process (visual, simple)"));
        document.add(new Paragraph("   • Clear timeline"));
        document.add(new Paragraph("   • What happens next"));
        
        document.add(new Paragraph("\n7. HUMANISING THE BRAND SECTION"));
        document.add(new Paragraph("   • Team photos"));
        document.add(new Paragraph("   • Founder story"));
        document.add(new Paragraph("   • Mission & origin story"));
        document.add(new Paragraph("   • Behind-the-scenes content"));
        
        document.add(new Paragraph("\n8. OBJECTIONS HANDLED / FUD REDUCTION"));
        document.add(new Paragraph("   • Money-back guarantees"));
        document.add(new Paragraph("   • Clear timelines"));
        document.add(new Paragraph("   • Transparent pricing"));
        document.add(new Paragraph("   • Secure payment badges"));
        document.add(new Paragraph("   • Contact details"));
        
        document.add(new Paragraph("\n9. FAQ SECTION"));
        document.add(new Paragraph("   • Place near bottom (decision moment)"));
        document.add(new Paragraph("   • Based on customer data (sales calls, support tickets)"));
        document.add(new Paragraph("   • Short, clear, helpful"));
        document.add(new Paragraph("   • Reduces friction"));
        
        document.add(new Paragraph("\n10. FINAL CTA + TRUST BADGES"));
        document.add(new Paragraph("   • Strong quantitative social proof"));
        document.add(new Paragraph("   • Last CTA"));
        document.add(new Paragraph("   • Guarantee"));
        document.add(new Paragraph("   • Mini-story / values"));
        document.add(new Paragraph("   • Soft CTA again"));
        
        document.add(new Paragraph("\n11. FOOTER"));
        document.add(new Paragraph("   • Contact information"));
        document.add(new Paragraph("   • Social media links"));
        document.add(new Paragraph("   • Navigation links"));
        document.add(new Paragraph("   • Legal links (Privacy Policy, Terms)"));
        
        document.add(new Paragraph("\n"));
    }

    private void addCopywritingGuidelines(Document document, ClientJourney cj) {
        document.add(new Paragraph("COPYWRITING REQUIREMENTS:"));
        document.add(new Paragraph("\n1. HEADLINE CREATION:"));
        document.add(new Paragraph("   Each headline MUST combine three critical elements:"));
        document.add(new Paragraph("   • EMOTIONAL DREAM OUTCOME - The transformed state the customer desires"));
        document.add(new Paragraph("   • FUNCTIONAL BENEFIT WITH TIMEFRAME - Specific, measurable result within concrete time"));
        document.add(new Paragraph("   • UNIQUE MECHANISM OR PROOF ELEMENT - What makes this possible or believable"));
        document.add(new Paragraph("   Example structure: '[Achieve Emotional Dream Outcome] [Functional Benefit] in [Timeframe] Using [Unique Mechanism] ([Proof Element])'"));
        document.add(new Paragraph("   • Make headlines BOLD, DIRECT, and BENEFIT-DRIVEN"));
        document.add(new Paragraph("   • Use direct response copywriting principles"));
        document.add(new Paragraph("   • NO em dashes (—) or en dashes (–)"));
        document.add(new Paragraph("   • Use ONLY ampersands (&), commas, or the word 'AND'"));
        
        document.add(new Paragraph("\n2. SUB-HEADLINE:"));
        document.add(new Paragraph("   • Builds on hero headline"));
        document.add(new Paragraph("   • Highlights unique mechanism"));
        document.add(new Paragraph("   • Calls out pain point, then transitions to solution"));
        document.add(new Paragraph("   • Ends with tangible, time-bound benefit"));
        document.add(new Paragraph("   • Maximum 1-2 sentences (30-40 words max)"));
        
        document.add(new Paragraph("\n3. PAIN POINT SECTION (Problem-Agitate-Solve Framework):"));
        document.add(new Paragraph("   OPENING: Start with 'Dear [Target Audience],' then list 3 common pain points as QUESTIONS"));
        document.add(new Paragraph("   PROBLEM: Acknowledge pain with empathy, use 'you' language"));
        document.add(new Paragraph("   AGITATE: Paint vivid picture of frustrations, show cost of inaction"));
        document.add(new Paragraph("   SOLVE: Transition with hope, clarify what they DON'T need, introduce what they DO need"));
        
        document.add(new Paragraph("\n4. VALUE PROPOSITIONS:"));
        document.add(new Paragraph("   • Identify 5-8 unique value propositions"));
        document.add(new Paragraph("   • Each must combine feature with emotional benefit"));
        document.add(new Paragraph("   • Answer 'What's in it for me?'"));
        document.add(new Paragraph("   • Focus on transformation: Freedom, Confidence, Security, Ease, Status"));
        document.add(new Paragraph("   • Keep headlines 12-18 words"));
        document.add(new Paragraph("   • Use concrete numbers, timeframes, guarantees"));
        
        document.add(new Paragraph("\n5. COPYWRITING RULES:"));
        document.add(new Paragraph("   • Best copy is borrowed from customer language (use their words verbatim)"));
        document.add(new Paragraph("   • Benefits > Features (what it does for ME, not what it is)"));
        document.add(new Paragraph("   • Conversational, human, simple"));
        document.add(new Paragraph("   • Short sentences (15 words or less)"));
        document.add(new Paragraph("   • Maximum 2 sentences per paragraph"));
        document.add(new Paragraph("   • Use single-line paragraphs for white space"));
        document.add(new Paragraph("   • Bold key ideas"));
        document.add(new Paragraph("   • Use bullet points with checkmarks"));
        document.add(new Paragraph("   • Write conversationally, like talking to a friend"));
        document.add(new Paragraph("   • Scan-friendly formatting (users scan, not read)"));
        
        document.add(new Paragraph("\n6. CTA REQUIREMENTS:"));
        document.add(new Paragraph("   • Value-driven, easy to understand, irresistible"));
        document.add(new Paragraph("   • Address concerns, offer risk-reducing incentives"));
        document.add(new Paragraph("   • Focus on 'What's in it for them'"));
        document.add(new Paragraph("   • Service-oriented, approachable"));
        document.add(new Paragraph("   • Deliver tangible benefits"));
        document.add(new Paragraph("   • Primary CTA: " + cj.getPrimaryCta()));
        document.add(new Paragraph("   • CTA Link/Phone: " + cj.getCtaLinkOrPhone()));
        if (cj.getSecondaryAction() != null) {
            document.add(new Paragraph("   • Secondary Action: " + cj.getSecondaryAction()));
        }
        
        document.add(new Paragraph("\n"));
    }

    private void addDesignPrinciples(Document document) {
        document.add(new Paragraph("CORE DESIGN PHILOSOPHY:"));
        document.add(new Paragraph("\n1. OPTIMISATION HAS NO CEILING"));
        document.add(new Paragraph("   • Treat optimisation as ongoing process"));
        document.add(new Paragraph("   • Never stop improving"));
        
        document.add(new Paragraph("\n2. PEOPLE BUY WITH EMOTION FIRST, LOGIC SECOND"));
        document.add(new Paragraph("   • Show transformation, not features"));
        document.add(new Paragraph("   • Use before/after"));
        document.add(new Paragraph("   • Use emotionally resonant testimonials"));
        document.add(new Paragraph("   • Appeal to dreams or pain relief"));
        
        document.add(new Paragraph("\n3. SIMPLICITY BEATS CREATIVITY"));
        document.add(new Paragraph("   • Clear > clever, always"));
        document.add(new Paragraph("   • 5-second test is non-negotiable"));
        
        document.add(new Paragraph("\n4. SCAN-READERS, NOT READERS"));
        document.add(new Paragraph("   • People read 20% of content, scan the rest"));
        document.add(new Paragraph("   • Build for scanners: headings, bullets, icons, spacing"));
        
        document.add(new Paragraph("\n5. VISUAL HIERARCHY & DESIGN:"));
        document.add(new Paragraph("   • Great design increases credibility"));
        document.add(new Paragraph("   • Use large headlines (48-57px desktop)"));
        document.add(new Paragraph("   • Body text = 16px desktop"));
        document.add(new Paragraph("   • Headline = 3-4× body text"));
        document.add(new Paragraph("   • High contrast CTA"));
        document.add(new Paragraph("   • Clear spacing"));
        document.add(new Paragraph("   • Logical section flow"));
        document.add(new Paragraph("   • Beautifully designed long pages convert well"));
        
        document.add(new Paragraph("\n6. REDUCE COGNITIVE LOAD:"));
        document.add(new Paragraph("   • Decision fatigue kills conversions"));
        document.add(new Paragraph("   • One main CTA"));
        document.add(new Paragraph("   • Limit navigation to <7 items"));
        document.add(new Paragraph("   • Simplify packages (2-3 max)"));
        document.add(new Paragraph("   • Remove unnecessary content"));
        
        document.add(new Paragraph("\n7. IMAGE STRATEGY:"));
        document.add(new Paragraph("   • Images must reinforce value prop (not decoration)"));
        document.add(new Paragraph("   • Show transformation (before/after, problem/solution)"));
        document.add(new Paragraph("   • Show real use in realistic settings"));
        document.add(new Paragraph("   • Show customer success (use images with faces)"));
        
        document.add(new Paragraph("\n"));
    }

    private void addPagesAndNavigation(Document document, ClientJourney cj) {
        document.add(new Paragraph("NAVIGATION REQUIREMENTS:"));
        document.add(new Paragraph("   • Limit navigation to less than 7 items"));
        document.add(new Paragraph("   • Clear, simple menu structure"));
        document.add(new Paragraph("   • Mobile-responsive hamburger menu"));
        
        document.add(new Paragraph("\nREQUIRED PAGES:"));
        document.add(new Paragraph("   1. HOME PAGE (Landing Page)"));
        document.add(new Paragraph("      • Follow structure outlined in Landing Page Structure section"));
        document.add(new Paragraph("      • Include animated logo carousel section"));
        document.add(new Paragraph("      • Buttons that navigate to specific pages"));
        
        if (cj.getServicesOrProducts() != null && !cj.getServicesOrProducts().isEmpty()) {
            document.add(new Paragraph("   2. SERVICES PAGE"));
            document.add(new Paragraph("      • Design cards for each service"));
            document.add(new Paragraph("      • Each card must include:"));
            document.add(new Paragraph("        - Service image"));
            document.add(new Paragraph("        - Service name"));
            document.add(new Paragraph("        - Service description"));
            document.add(new Paragraph("      • Use grid layout (responsive)"));
            document.add(new Paragraph("      • Include CTA on each card"));
        }
        
        document.add(new Paragraph("   3. ABOUT PAGE"));
        document.add(new Paragraph("      • Company story"));
        document.add(new Paragraph("      • Mission & values"));
        document.add(new Paragraph("      • Team information"));
        document.add(new Paragraph("      • Founder story"));
        
        document.add(new Paragraph("   4. CONTACT PAGE"));
        document.add(new Paragraph("      • Business phone: " + cj.getBusinessPhone()));
        document.add(new Paragraph("      • Business email: " + cj.getBusinessEmail()));
        document.add(new Paragraph("      • Business location: " + cj.getBusinessLocation()));
        if (cj.getOpeningDays() != null) {
            document.add(new Paragraph("      • Opening days: " + cj.getOpeningDays().getOpeningDays()));
        }
        if (cj.getCustomHours() != null) {
            document.add(new Paragraph("      • Operating hours: " + cj.getCustomHours()));
        }
        document.add(new Paragraph("      • Contact form"));
        document.add(new Paragraph("      • Map (if applicable)"));
        
        document.add(new Paragraph("\nPAGE NAVIGATION BEHAVIOUR:"));
        document.add(new Paragraph("   • When clicking on pages or navigating, always start at the top"));
        document.add(new Paragraph("   • Smooth scroll to top on page load"));
        document.add(new Paragraph("   • No auto-scroll to middle sections"));
        
        document.add(new Paragraph("\n"));
    }

    private void addTechnicalRequirements(Document document) {
        document.add(new Paragraph("TECHNICAL REQUIREMENTS:"));
        document.add(new Paragraph("\n1. RESPONSIVENESS:"));
        document.add(new Paragraph("   • MUST be fully responsive to any screen size"));
        document.add(new Paragraph("   • Mobile-first approach"));
        document.add(new Paragraph("   • Test on: Mobile (320px+), Tablet (768px+), Desktop (1024px+)"));
        document.add(new Paragraph("   • All elements must adapt properly"));
        
        document.add(new Paragraph("\n2. LANGUAGE & SPELLING:"));
        document.add(new Paragraph("   • ALWAYS use British English spelling"));
        document.add(new Paragraph("   • Examples:"));
        document.add(new Paragraph("     - 'Specialized' → 'Specialised'"));
        document.add(new Paragraph("     - 'Organize' → 'Organise'"));
        document.add(new Paragraph("     - 'Color' → 'Colour'"));
        document.add(new Paragraph("     - 'Center' → 'Centre'"));
        document.add(new Paragraph("   • Check all text for British English"));
        
        document.add(new Paragraph("\n3. ANIMATIONS & INTERACTIONS:"));
        document.add(new Paragraph("   • Include animated logo carousel section"));
        document.add(new Paragraph("   • Smooth transitions"));
        document.add(new Paragraph("   • Hover effects on interactive elements"));
        document.add(new Paragraph("   • Scroll animations (subtle, not distracting)"));
        
        document.add(new Paragraph("\n4. PERFORMANCE:"));
        document.add(new Paragraph("   • Optimise images (compress, lazy load)"));
        document.add(new Paragraph("   • Fast page load times"));
        document.add(new Paragraph("   • SEO-friendly structure"));
        
        document.add(new Paragraph("\n5. ACCESSIBILITY:"));
        document.add(new Paragraph("   • Alt text for all images"));
        document.add(new Paragraph("   • Proper heading hierarchy"));
        document.add(new Paragraph("   • Keyboard navigation support"));
        document.add(new Paragraph("   • ARIA labels where needed"));
        
        document.add(new Paragraph("\n"));
    }

    private void addContentRequirements(Document document, ClientJourney cj) {
        document.add(new Paragraph("CONTENT REQUIREMENTS:"));
        document.add(new Paragraph("\n1. BUSINESS INFORMATION:"));
        document.add(new Paragraph("   • Company Name: " + cj.getCompanyName()));
        document.add(new Paragraph("   • About: " + cj.getAboutInformation()));
        if (cj.getIndustry() != null) {
            document.add(new Paragraph("   • Industry: " + cj.getIndustry().getIndustry()));
        }
        
        document.add(new Paragraph("\n2. IMAGES PROVIDED:"));
        if (cj.getLogoFileName() != null) {
            document.add(new Paragraph("   • Logo: " + cj.getLogoFileName()));
            document.add(new Paragraph("     URL: " + cj.getLogoFileUrl()));
        }
        if (cj.getOptionalImages() != null && !cj.getOptionalImages().isEmpty()) {
            document.add(new Paragraph("   • Additional Images (" + cj.getOptionalImages().size() + "):"));
            for (com.example.simplyfound.models.OptionalImage img : cj.getOptionalImages()) {
                document.add(new Paragraph("     - " + img.getFileName() + " (" + img.getFileUrl() + ")"));
            }
        }
        
        document.add(new Paragraph("\n3. SOCIAL MEDIA LINKS:"));
        if (cj.getFacebookUrl() != null) {
            document.add(new Paragraph("   • Facebook: " + cj.getFacebookUrl()));
        }
        if (cj.getInstagramUrl() != null) {
            document.add(new Paragraph("   • Instagram: " + cj.getInstagramUrl()));
        }
        if (cj.getGoogleBusinessUrl() != null) {
            document.add(new Paragraph("   • Google Business: " + cj.getGoogleBusinessUrl()));
        }
        if (cj.getTiktokUrl() != null) {
            document.add(new Paragraph("   • TikTok: " + cj.getTiktokUrl()));
        }
        
        document.add(new Paragraph("\n4. TESTIMONIALS:"));
        if (cj.getTestimonials() != null && !cj.getTestimonials().trim().isEmpty()) {
            document.add(new Paragraph("   " + cj.getTestimonials()));
        } else {
            document.add(new Paragraph("   • Include customer testimonials"));
            document.add(new Paragraph("   • Match testimonials to pain points"));
            document.add(new Paragraph("   • Use transformation stories"));
        }
        
        document.add(new Paragraph("\n"));
    }

    private void addServicesProductsDetailed(Document document, ClientJourney cj) {
        if (cj.getServicesOrProducts() != null && !cj.getServicesOrProducts().isEmpty()) {
            document.add(new Paragraph("TOTAL SERVICES/PRODUCTS: " + cj.getServicesOrProducts().size()));
            document.add(new Paragraph("\nDETAILED LIST OF ALL SERVICES/PRODUCTS:"));
            
            int index = 1;
            for (com.example.simplyfound.models.ServiceOrProduct service : cj.getServicesOrProducts()) {
                document.add(new Paragraph("\n" + index + ". SERVICE/PRODUCT NAME: " + service.getName()).setBold());
                document.add(new Paragraph("   Description: " + service.getDescription()));
                document.add(new Paragraph("   Service ID: " + service.getId()));
                document.add(new Paragraph("   Created: " + (service.getCreatedAt() != null ? service.getCreatedAt().toString() : "N/A")));
                document.add(new Paragraph("   "));
                document.add(new Paragraph("   DESIGN REQUIREMENTS FOR THIS SERVICE/PRODUCT:"));
                document.add(new Paragraph("   • Create a card/component for this service"));
                document.add(new Paragraph("   • Include service name prominently"));
                document.add(new Paragraph("   • Display full description"));
                document.add(new Paragraph("   • Add an image placeholder (use provided images if available)"));
                document.add(new Paragraph("   • Include a CTA button (e.g., 'Learn More', 'Get Quote', 'Contact Us')"));
                document.add(new Paragraph("   • Make card clickable/interactive"));
                document.add(new Paragraph("   • Ensure card is responsive"));
                index++;
            }
            
            document.add(new Paragraph("\nSERVICES PAGE DESIGN REQUIREMENTS:"));
            document.add(new Paragraph("• Display all " + cj.getServicesOrProducts().size() + " services/products in a grid layout"));
            document.add(new Paragraph("• Use responsive grid (2-3 columns on desktop, 1 column on mobile)"));
            document.add(new Paragraph("• Each service card must include:"));
            document.add(new Paragraph("  - Service name (as provided above)"));
            document.add(new Paragraph("  - Service description (as provided above)"));
            document.add(new Paragraph("  - Image (use provided optional images or placeholder)"));
            document.add(new Paragraph("  - CTA button"));
            document.add(new Paragraph("• Ensure all services are displayed"));
            document.add(new Paragraph("• Maintain consistent card design across all services"));
        } else {
            document.add(new Paragraph("NO SERVICES OR PRODUCTS PROVIDED"));
            document.add(new Paragraph("The client did not submit any services or products in the form."));
            document.add(new Paragraph("If services/products are needed, contact the client for this information."));
        }
        document.add(new Paragraph("\n"));
    }

    private void addOptionalImagesDetailed(Document document, ClientJourney cj) {
        if (cj.getOptionalImages() != null && !cj.getOptionalImages().isEmpty()) {
            document.add(new Paragraph("TOTAL OPTIONAL IMAGES PROVIDED: " + cj.getOptionalImages().size()));
            document.add(new Paragraph("Maximum allowed: 10 images"));
            document.add(new Paragraph("\nDETAILED LIST OF ALL OPTIONAL IMAGES:"));
            
            int index = 1;
            for (com.example.simplyfound.models.OptionalImage image : cj.getOptionalImages()) {
                document.add(new Paragraph("\n" + index + ". IMAGE DETAILS:").setBold());
                document.add(new Paragraph("   File Name: " + image.getFileName()));
                document.add(new Paragraph("   File URL: " + image.getFileUrl()));
                document.add(new Paragraph("   File Type: " + image.getFileType()));
                document.add(new Paragraph("   Image ID: " + image.getId()));
                document.add(new Paragraph("   Created: " + (image.getCreatedAt() != null ? image.getCreatedAt().toString() : "N/A")));
                index++;
            }
            
            document.add(new Paragraph("\nHOW TO USE THESE IMAGES:"));
            document.add(new Paragraph("• Use these images throughout the website"));
            document.add(new Paragraph("• Services page: Use images for service cards"));
            document.add(new Paragraph("• Landing page: Use images in hero section, benefits section, transformation section"));
            document.add(new Paragraph("• About page: Use images to showcase business"));
            document.add(new Paragraph("• Gallery section: Create an image gallery/carousel"));
            document.add(new Paragraph("• Optimise all images for web (compress, lazy load)"));
            document.add(new Paragraph("• Ensure all images have proper alt text"));
            document.add(new Paragraph("• Use images that reinforce the value proposition"));
            
            document.add(new Paragraph("\nIMAGE ACCESS:"));
            document.add(new Paragraph("All images can be accessed via the provided URLs above."));
            document.add(new Paragraph("Base URL: http://localhost:8080"));
            document.add(new Paragraph("Full image URLs are provided in the File URL field above."));
        } else {
            document.add(new Paragraph("NO OPTIONAL IMAGES PROVIDED"));
            document.add(new Paragraph("The client did not upload any optional images."));
            document.add(new Paragraph("IMPORTANT: Since no images were uploaded, you MUST generate images using AI tools (see 'AI IMAGE GENERATION REQUIREMENTS' section below)."));
            document.add(new Paragraph("DO NOT use generic placeholder images or stock photos - generate custom images based on the business."));
        }
        
        // Logo Information
        document.add(new Paragraph("\nLOGO INFORMATION:"));
        if (cj.getLogoFileName() != null) {
            document.add(new Paragraph("• Logo File Name: " + cj.getLogoFileName()).setBold());
            document.add(new Paragraph("• Logo File URL: " + cj.getLogoFileUrl()));
            document.add(new Paragraph("• Logo File Type: " + cj.getLogoFileType()));
            document.add(new Paragraph("• Use this logo in:"));
            document.add(new Paragraph("  - Header/Navigation bar"));
            document.add(new Paragraph("  - Footer"));
            document.add(new Paragraph("  - Favicon (if applicable)"));
            document.add(new Paragraph("  - Email templates"));
            document.add(new Paragraph("  - Social media previews"));
        } else {
            document.add(new Paragraph("• No logo was uploaded by the client"));
            document.add(new Paragraph("• Create a text-based logo using company name: " + cj.getCompanyName()));
            document.add(new Paragraph("• Or use a placeholder logo"));
        }
        
        document.add(new Paragraph("\n"));
    }

    private void addImageGenerationRequirements(Document document, ClientJourney cj) {
        document.add(new Paragraph("CRITICAL: Since the client did not upload any images, you MUST generate custom images using AI image generation tools (such as DALL-E, Midjourney, Stable Diffusion, or similar)."));
        document.add(new Paragraph("DO NOT use generic placeholder images or stock photos."));
        
        document.add(new Paragraph("\nMANDATORY REQUIREMENTS FOR ALL GENERATED IMAGES:"));
        document.add(new Paragraph("1. NAMIBIAN CONTEXT:"));
        document.add(new Paragraph("   • All images MUST be set in Namibian context"));
        document.add(new Paragraph("   • Include Namibian landscapes, architecture, or cultural elements where relevant"));
        document.add(new Paragraph("   • Show Namibian business environments"));
        document.add(new Paragraph("   • Reflect Namibian market and local context"));
        
        document.add(new Paragraph("\n2. REPRESENTATION REQUIREMENT:"));
        document.add(new Paragraph("   • ALL images MUST include black people"));
        document.add(new Paragraph("   • Show diverse representation of Namibian people"));
        document.add(new Paragraph("   • People in images should reflect the local Namibian demographic"));
        document.add(new Paragraph("   • If showing customers, employees, or business interactions, ensure black people are prominently featured"));
        
        document.add(new Paragraph("\n3. BUSINESS-SPECIFIC IMAGES:"));
        document.add(new Paragraph("   • Generate images that directly relate to what the business does"));
        document.add(new Paragraph("   • Company Name: " + cj.getCompanyName()));
        document.add(new Paragraph("   • Business Description: " + cj.getAboutInformation()));
        if (cj.getIndustry() != null) {
            document.add(new Paragraph("   • Industry: " + cj.getIndustry().getIndustry()));
        }
        
        document.add(new Paragraph("\n4. IMAGES NEEDED FOR DIFFERENT SECTIONS:"));
        document.add(new Paragraph("   a) HERO SECTION IMAGE:"));
        document.add(new Paragraph("      • Main hero image that represents the business"));
        document.add(new Paragraph("      • Should visually communicate what the business does"));
        document.add(new Paragraph("      • Include Namibian context and black people"));
        document.add(new Paragraph("      • Professional, high-quality, engaging"));
        
        if (cj.getServicesOrProducts() != null && !cj.getServicesOrProducts().isEmpty()) {
            document.add(new Paragraph("\n   b) SERVICE/PRODUCT IMAGES:"));
            document.add(new Paragraph("      • Generate one image per service/product"));
            int index = 1;
            for (com.example.simplyfound.models.ServiceOrProduct service : cj.getServicesOrProducts()) {
                document.add(new Paragraph("      • Image " + index + " for: " + service.getName()));
                document.add(new Paragraph("        Description: " + service.getDescription()));
                document.add(new Paragraph("        Must show: " + service.getName() + " in Namibian context with black people"));
                index++;
            }
        }
        
        document.add(new Paragraph("\n   c) ABOUT PAGE IMAGES:"));
        document.add(new Paragraph("      • Team photos (if applicable)"));
        document.add(new Paragraph("      • Business location or workspace"));
        document.add(new Paragraph("      • Show the business in action"));
        document.add(new Paragraph("      • Include black people in business settings"));
        
        document.add(new Paragraph("\n   d) GALLERY/CAROUSEL IMAGES:"));
        document.add(new Paragraph("      • 3-5 additional images showcasing the business"));
        document.add(new Paragraph("      • Variety of scenes related to the business"));
        document.add(new Paragraph("      • All with Namibian context and black people"));
        
        document.add(new Paragraph("\n5. AI IMAGE GENERATION PROMPTS:"));
        document.add(new Paragraph("   Use these prompt templates when generating images:"));
        
        document.add(new Paragraph("\n   HERO IMAGE PROMPT TEMPLATE:"));
        document.add(new Paragraph("   \"Professional [business type] image set in Namibia, showing [what business does], featuring black Namibian people, [specific business context], high quality, modern, vibrant colours, professional photography style\""));
        
        if (cj.getServicesOrProducts() != null && !cj.getServicesOrProducts().isEmpty()) {
            document.add(new Paragraph("\n   SERVICE/PRODUCT IMAGE PROMPT TEMPLATE:"));
            for (com.example.simplyfound.models.ServiceOrProduct service : cj.getServicesOrProducts()) {
                document.add(new Paragraph("   For " + service.getName() + ":"));
                document.add(new Paragraph("   \"[Service/Product name] in Namibian setting, showing [service description], featuring black Namibian people, professional, high quality, relevant to " + cj.getCompanyName() + "\""));
            }
        }
        
        document.add(new Paragraph("\n6. IMAGE SPECIFICATIONS:"));
        document.add(new Paragraph("   • Resolution: Minimum 1920x1080px (Full HD)"));
        document.add(new Paragraph("   • Format: JPG or PNG"));
        document.add(new Paragraph("   • Quality: High resolution, professional quality"));
        document.add(new Paragraph("   • Style: Modern, professional, appropriate for business website"));
        document.add(new Paragraph("   • Optimise for web after generation (compress without losing quality)"));
        
        document.add(new Paragraph("\n7. IMAGE GENERATION TOOLS:"));
        document.add(new Paragraph("   • Recommended: DALL-E 3, Midjourney, Stable Diffusion, or similar AI image generators"));
        document.add(new Paragraph("   • Use the prompts provided above"));
        document.add(new Paragraph("   • Generate multiple variations and select the best ones"));
        document.add(new Paragraph("   • Ensure all images meet the Namibian context and black people requirements"));
        
        document.add(new Paragraph("\n8. QUALITY CHECKLIST:"));
        document.add(new Paragraph("   ✓ Image is set in Namibian context"));
        document.add(new Paragraph("   ✓ Black people are prominently featured"));
        document.add(new Paragraph("   ✓ Image relates directly to the business/services"));
        document.add(new Paragraph("   ✓ Professional quality and appropriate for website"));
        document.add(new Paragraph("   ✓ High resolution and web-optimised"));
        document.add(new Paragraph("   ✓ Reflects the business brand and values"));
        
        document.add(new Paragraph("\n9. IMPORTANT NOTES:"));
        document.add(new Paragraph("   • DO NOT skip image generation - it is mandatory when no images are uploaded"));
        document.add(new Paragraph("   • All images must be generated, not sourced from stock photo sites"));
        document.add(new Paragraph("   • Ensure cultural sensitivity and authentic representation"));
        document.add(new Paragraph("   • Images should enhance the website's visual appeal and professionalism"));
        document.add(new Paragraph("   • Generate enough images to populate all sections of the website"));
        
        document.add(new Paragraph("\n"));
    }

    private void addSocialProof(Document document, ClientJourney cj) {
        document.add(new Paragraph("SOCIAL PROOF REQUIREMENTS:"));
        document.add(new Paragraph("\n1. TESTIMONIAL RELEVANCE > QUANTITY"));
        document.add(new Paragraph("   • Match each testimonial to a core pain point"));
        document.add(new Paragraph("   • Use transformation stories (before → after)"));
        
        document.add(new Paragraph("\n2. USE NUMBERS OVER WORDS"));
        document.add(new Paragraph("   • Visitors trust quantified claims more"));
        document.add(new Paragraph("   • Examples: 'Trusted by 12,487 customers', 'Cut costs by 37%'"));
        
        document.add(new Paragraph("\n3. PLACE SOCIAL PROOF AT DECISION POINTS"));
        document.add(new Paragraph("   • Above the fold"));
        document.add(new Paragraph("   • Next to CTAs"));
        document.add(new Paragraph("   • Near pricing"));
        document.add(new Paragraph("   • Bottom of page"));
        
        if (cj.getTestimonials() != null && !cj.getTestimonials().trim().isEmpty()) {
            document.add(new Paragraph("\n4. PROVIDED TESTIMONIALS:"));
            document.add(new Paragraph("   " + cj.getTestimonials()));
        }
        
        document.add(new Paragraph("\n"));
    }

    private void addCtaRequirements(Document document, ClientJourney cj) {
        document.add(new Paragraph("CALL-TO-ACTION REQUIREMENTS:"));
        document.add(new Paragraph("\nPRIMARY CTA:"));
        document.add(new Paragraph("   • Type: " + cj.getPrimaryCta()));
        document.add(new Paragraph("   • Link/Phone: " + cj.getCtaLinkOrPhone()));
        document.add(new Paragraph("   • Must be value-driven"));
        document.add(new Paragraph("   • Easy to understand"));
        document.add(new Paragraph("   • Irresistible"));
        document.add(new Paragraph("   • Address concerns"));
        document.add(new Paragraph("   • Offer risk-reducing incentives"));
        
        if (cj.getSecondaryAction() != null) {
            document.add(new Paragraph("\nSECONDARY ACTION:"));
            document.add(new Paragraph("   • " + cj.getSecondaryAction()));
        }
        
        document.add(new Paragraph("\nCTA PLACEMENT:"));
        document.add(new Paragraph("   • Above the fold (hero section)"));
        document.add(new Paragraph("   • After each major section"));
        document.add(new Paragraph("   • Bottom of page"));
        document.add(new Paragraph("   • Use soft CTAs: 'See How It Works', 'Get My Free Quote', 'Get Instant Access'"));
        
        document.add(new Paragraph("\n"));
    }

    private void addFooterRequirements(Document document, ClientJourney cj) {
        document.add(new Paragraph("FOOTER REQUIREMENTS:"));
        document.add(new Paragraph("\nMUST INCLUDE:"));
        document.add(new Paragraph("   • Company Name: " + cj.getCompanyName()));
        document.add(new Paragraph("   • Business Phone: " + cj.getBusinessPhone()));
        document.add(new Paragraph("   • Business Email: " + cj.getBusinessEmail()));
        document.add(new Paragraph("   • Business Location: " + cj.getBusinessLocation()));
        
        if (cj.getOpeningDays() != null) {
            document.add(new Paragraph("   • Opening Days: " + cj.getOpeningDays().getOpeningDays()));
        }
        if (cj.getCustomHours() != null) {
            document.add(new Paragraph("   • Operating Hours: " + cj.getCustomHours()));
        }
        
        document.add(new Paragraph("   • Social Media Links:"));
        if (cj.getFacebookUrl() != null) {
            document.add(new Paragraph("     - Facebook: " + cj.getFacebookUrl()));
        }
        if (cj.getInstagramUrl() != null) {
            document.add(new Paragraph("     - Instagram: " + cj.getInstagramUrl()));
        }
        if (cj.getGoogleBusinessUrl() != null) {
            document.add(new Paragraph("     - Google Business: " + cj.getGoogleBusinessUrl()));
        }
        if (cj.getTiktokUrl() != null) {
            document.add(new Paragraph("     - TikTok: " + cj.getTiktokUrl()));
        }
        
        document.add(new Paragraph("   • Navigation Links"));
        document.add(new Paragraph("   • Legal Links (Privacy Policy, Terms & Conditions)"));
        document.add(new Paragraph("   • Copyright notice"));
        
        document.add(new Paragraph("\n"));
    }
}

