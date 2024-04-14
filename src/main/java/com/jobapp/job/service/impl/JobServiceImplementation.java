package com.jobapp.job.service.impl;

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

    @Autowired
    RestTemplate restTemplate;

//    Since JobRepository is a bean managed by Spring at the run time, we are creating the
//    constructor so that Spring automatically injects it during runtime, and we don't have to
//    do it manually
    public JobServiceImplementation(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobDto> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private JobDto convertToDto(Job job){
//        useful getforobject when response has single entity
        Company company = restTemplate.getForObject("http://COMPANY:8082/company/" + job.getCompanyId(), Company.class);
//        when having a list of entities in response better to handle it via exchange method
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW:8083/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });
        List<Review> reviews = reviewResponse.getBody();
        JobDto jobDto = JobMapper.mapToJobDto(job, company, reviews);
        return jobDto;
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
            Optional<Job> companyOptional = jobRepository.findById(id);
            if (companyOptional.isPresent()) {
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
