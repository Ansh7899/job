package com.jobapp.job.service.impl;

import com.jobapp.job.clients.CompanyClient;
import com.jobapp.job.clients.ReviewClient;
import com.jobapp.job.dto.JobDto;
import com.jobapp.job.external.Company;
import com.jobapp.job.external.Review;
import com.jobapp.job.mapper.JobMapper;
import com.jobapp.job.model.Job;
import com.jobapp.job.repository.JobRepository;
import com.jobapp.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImplementation implements JobService {
    JobRepository jobRepository;

    private final CompanyClient companyClient;

    private final ReviewClient reviewClient;

//    Since JobRepository is a bean managed by Spring at the run time, we are creating the
//    constructor so that Spring automatically injects it during runtime, and we don't have to
//    do it manually
    public JobServiceImplementation(JobRepository jobRepository,
                                    CompanyClient companyClient,
                                    ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDto> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private JobDto convertToDto(Job job){
//        useful getforobject when response has single entity
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviewsForCompany(job.getCompanyId());
        return JobMapper.mapToJobDto(job, company, reviews);
    }

    @Override
    public JobDto findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            return convertToDto(job);
        }
        return null;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.saveAndFlush(job);
    }

    @Override
    public Boolean deleteJob(Long id) {
        try {
            Optional<Job> jobOptional = jobRepository.findById(id);
            if (jobOptional.isPresent()) {
                jobRepository.deleteById(id);
                return true; // Deletion successful
            } else {
                return false; // Entity with given ID doesn't exist
            }
        } catch (Exception e) {
            return false; // Deletion unsuccessful due to exception
        }
    }


    @Override
    public Boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
            if (jobOptional.isPresent()) {
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setLocation(updatedJob.getLocation());
                job.setCompanyId(updatedJob.getCompanyId());
                jobRepository.saveAndFlush(job);
                return true;
            }
        return false;
    }

}
