package com.bryan.Florida_Job_Market_Dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final JobRepository jobRepository;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public DashboardController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    // ==========================================
    // DASHBOARD
    // ==========================================

    @GetMapping("/")
    public String dashboard(Model model) {

        // Get all jobs from MySQL
        List<Job> jobs = jobRepository.findAll();


        // ==========================================
        // TOTAL JOBS
        // ==========================================

        long totalJobs = jobs.size();


        // ==========================================
        // JOBS WITH SALARY
        // ==========================================

        long jobsWithSalary = jobs.stream()
                .filter(job ->
                        (job.getSalaryMin() != null && job.getSalaryMin() > 0)
                                ||
                                (job.getSalaryMax() != null && job.getSalaryMax() > 0)
                )
                .count();


        // ==========================================
        // AVERAGE SALARY
        // ==========================================

        double averageSalary = jobs.stream()
                .filter(job ->
                        job.getSalaryMin() != null
                                &&
                                job.getSalaryMin() > 0
                )
                .mapToDouble(Job::getSalaryMin)
                .average()
                .orElse(0.0);


        // ==========================================
        // TOP LOCATION
        // ==========================================

        String topLocation = jobs.stream()

                .map(Job::getLocation)

                .filter(location ->
                        location != null
                                &&
                                !location.isBlank()
                )

                .collect(Collectors.groupingBy(
                        location -> location,
                        Collectors.counting()
                ))

                .entrySet()

                .stream()

                .max(
                        Comparator.comparingLong(
                                Map.Entry::getValue
                        )
                )

                .map(Map.Entry::getKey)

                .orElse("N/A");


        // ==========================================
        // TOP COMPANY
        // ==========================================

        String topCompany = jobs.stream()

                .map(Job::getCompany)

                .filter(company ->
                        company != null
                                &&
                                !company.isBlank()
                )

                .collect(Collectors.groupingBy(
                        company -> company,
                        Collectors.counting()
                ))

                .entrySet()

                .stream()

                .max(
                        Comparator.comparingLong(
                                Map.Entry::getValue
                        )
                )

                .map(Map.Entry::getKey)

                .orElse("N/A");


        // ==========================================
        // SEND DATA TO THYMELEAF
        // ==========================================

        model.addAttribute(
                "jobs",
                jobs
        );

        model.addAttribute(
                "totalJobs",
                totalJobs
        );

        model.addAttribute(
                "jobsWithSalary",
                jobsWithSalary
        );

        model.addAttribute(
                "averageSalary",
                averageSalary
        );

        model.addAttribute(
                "topLocation",
                topLocation
        );

        model.addAttribute(
                "topCompany",
                topCompany
        );


        return "dashboard";
    }
}