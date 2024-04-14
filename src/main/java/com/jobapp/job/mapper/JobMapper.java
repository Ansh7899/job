package com.jobapp.job.mapper;

import com.jobapp.job.dto.JobDto;
import com.jobapp.job.external.Company;
import com.jobapp.job.external.Review;
import com.jobapp.job.model.Job;

import java.util.List;

public class JobMapper {

    public static JobDto mapToJobDto (
            Job job, Company company, List<Review> reviews
    ) {
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setDescription(job.getDescription());
        jobDto.setLocation(job.getLocation());
        jobDto.setMaxSalary(job.getMaxSalary());
        jobDto.setMinSalary(job.getMinSalary());
        jobDto.setCompany(company);
        jobDto.setReview(reviews);
        return null;
    }
}
