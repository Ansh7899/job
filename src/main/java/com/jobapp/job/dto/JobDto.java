package com.jobapp.job.dto;

import com.jobapp.job.external.Company;
import com.jobapp.job.external.Review;
import com.jobapp.job.model.Job;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobDto {
    private Long id;
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    private String location;
    private Company company;
    private List<Review> review;
}
