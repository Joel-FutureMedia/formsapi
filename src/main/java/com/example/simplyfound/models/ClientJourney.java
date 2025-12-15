package com.example.simplyfound.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client_journeys")
public class ClientJourney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Step 1 - Basic Info
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Column(nullable = false)
    private String businessLocation;

    // Step 2 - Business Identity
    @Column(nullable = false, columnDefinition = "TEXT")
    private String aboutInformation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column
    private String logoFileName;

    @Column
    private String logoFileUrl;

    @Column
    private String logoFileType;

    // Step 3 - Contact Info
    @Column(nullable = false)
    private String businessPhone;

    @Column(nullable = false)
    private String businessEmail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opening_days_id")
    private OpeningDays openingDays;

    @Column
    private String customHours;

    // Step 4 - Services/Products (handled via relationship)
    @OneToMany(mappedBy = "clientJourney", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ServiceOrProduct> servicesOrProducts = new ArrayList<>();

    // Step 5 - Visual Style
    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "secondary_color")
    private String secondaryColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "layout_style_id")
    private LayoutStyle layoutStyle;

    // Step 5 - Optional Images (handled via relationship)
    @OneToMany(mappedBy = "clientJourney", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OptionalImage> optionalImages = new ArrayList<>();

    // Step 6 - Social Links & Trust Builders
    @Column
    private String facebookUrl;

    @Column
    private String instagramUrl;

    @Column
    private String googleBusinessUrl;

    @Column
    private String tiktokUrl;

    @Column(columnDefinition = "TEXT")
    private String testimonials;

    // Step 7 - Calls to Action
    @Column(nullable = false)
    private String primaryCta;

    @Column(nullable = false)
    private String ctaLinkOrPhone;

    @Column
    private String secondaryAction;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public ClientJourney() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getAboutInformation() {
        return aboutInformation;
    }

    public void setAboutInformation(String aboutInformation) {
        this.aboutInformation = aboutInformation;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }

    public String getLogoFileUrl() {
        return logoFileUrl;
    }

    public void setLogoFileUrl(String logoFileUrl) {
        this.logoFileUrl = logoFileUrl;
    }

    public String getLogoFileType() {
        return logoFileType;
    }

    public void setLogoFileType(String logoFileType) {
        this.logoFileType = logoFileType;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public OpeningDays getOpeningDays() {
        return openingDays;
    }

    public void setOpeningDays(OpeningDays openingDays) {
        this.openingDays = openingDays;
    }

    public String getCustomHours() {
        return customHours;
    }

    public void setCustomHours(String customHours) {
        this.customHours = customHours;
    }

    public List<ServiceOrProduct> getServicesOrProducts() {
        return servicesOrProducts;
    }

    public void setServicesOrProducts(List<ServiceOrProduct> servicesOrProducts) {
        this.servicesOrProducts = servicesOrProducts;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public LayoutStyle getLayoutStyle() {
        return layoutStyle;
    }

    public void setLayoutStyle(LayoutStyle layoutStyle) {
        this.layoutStyle = layoutStyle;
    }

    public List<OptionalImage> getOptionalImages() {
        return optionalImages;
    }

    public void setOptionalImages(List<OptionalImage> optionalImages) {
        this.optionalImages = optionalImages;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getGoogleBusinessUrl() {
        return googleBusinessUrl;
    }

    public void setGoogleBusinessUrl(String googleBusinessUrl) {
        this.googleBusinessUrl = googleBusinessUrl;
    }

    public String getTiktokUrl() {
        return tiktokUrl;
    }

    public void setTiktokUrl(String tiktokUrl) {
        this.tiktokUrl = tiktokUrl;
    }

    public String getTestimonials() {
        return testimonials;
    }

    public void setTestimonials(String testimonials) {
        this.testimonials = testimonials;
    }

    public String getPrimaryCta() {
        return primaryCta;
    }

    public void setPrimaryCta(String primaryCta) {
        this.primaryCta = primaryCta;
    }

    public String getCtaLinkOrPhone() {
        return ctaLinkOrPhone;
    }

    public void setCtaLinkOrPhone(String ctaLinkOrPhone) {
        this.ctaLinkOrPhone = ctaLinkOrPhone;
    }

    public String getSecondaryAction() {
        return secondaryAction;
    }

    public void setSecondaryAction(String secondaryAction) {
        this.secondaryAction = secondaryAction;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

