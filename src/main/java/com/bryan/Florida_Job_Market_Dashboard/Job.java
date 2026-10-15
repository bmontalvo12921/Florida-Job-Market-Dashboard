package com.bryan.Florida_Job_Market_Dashboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "location")
    private String location;

    @Column(name = "category")
    private String category;

    @Column(name = "salary_min")
    private Double salaryMin;

    @Column(name = "salary_max")
    private Double salaryMax;

    @Column(name = "salary_predicted")
    private Boolean salaryPredicted;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "contract_time")
    private String contractTime;

    @Column(name = "description")
    private String description;

    @Column(name = "job_url")
    private String jobUrl;

    @Column(name = "created")
    private String created;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "education_requirement")
    private String educationRequirement;

    // Required by JPA
    public Job() {
    }

    // =========================
    // GETTERS AND SETTERS
    // =========================

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public Boolean getSalaryPredicted() {
        return salaryPredicted;
    }

    public void setSalaryPredicted(Boolean salaryPredicted) {
        this.salaryPredicted = salaryPredicted;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getEducationRequirement() {
        return educationRequirement;
    }

    public void setEducationRequirement(String educationRequirement) {
        this.educationRequirement = educationRequirement;
    }

    // =========================
    // LOCATION NORMALIZATION
    // =========================

    /**
     * Creates a dashboard-friendly version of the location.
     *
     * Examples:
     * "Orlando, Orange County" -> "Orlando"
     * "Tallahassee, Leon County" -> "Tallahassee"
     * "Fort Lauderdale, Broward County" -> "Fort Lauderdale"
     * "Florida" -> "Statewide"
     * "Florida, US" -> "Statewide"
     */
    public String getNormalizedLocation() {

        if (location == null || location.isBlank()) {
            return "Unknown";
        }

        String normalized = location.trim();

        // Treat statewide Florida listings as one location
        if (normalized.equalsIgnoreCase("Florida")
                || normalized.equalsIgnoreCase("Florida, US")
                || normalized.equalsIgnoreCase("Florida, USA")) {

            return "Statewide";
        }

        // For locations such as:
        // Orlando, Orange County
        // Tampa, Hillsborough County
        // Miami, Miami-Dade County
        if (normalized.contains(",")) {

            String city = normalized.split(",", 2)[0].trim();

            if (!city.isBlank()) {
                return city;
            }
        }

        // If there is no comma, keep the original location
        return normalized;
    }
}