package com.jobapp.job.service.impl;

import com.jobapp.job.dto.JobWithCompanyDto;
import com.jobapp.job.external.Company;
import com.jobapp.job.model.Job;
import com.jobapp.job.repository.JobRepository;
import com.jobapp.job.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImplementation implements JobService {
    JobRepository jobRepository;

//    Since JobRepository is a bean managed by Spring at the run time, we are creating the
//    constructor so that Spring automatically injects it during runtime, and we don't have to
//    do it manually
    public JobServiceImplementation(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDto> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDto> jobWithCompanyDtos = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        for (Job job:
             jobs) {
            JobWithCompanyDto jobWithCompanyDto = new JobWithCompanyDto();
            jobWithCompanyDto.setJob(job);
            Company company = restTemplate.getForObject("http://localhost:8082/company/" + job.getCompanyId(), Company.class);
            jobWithCompanyDto.setCompany(company);
            jobWithCompanyDtos.add(jobWithCompanyDto);
        }
        return jobWithCompanyDtos;
    }

    @Override
    public Job findJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
                job.setDestination(updatedJob.getDestination());
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
