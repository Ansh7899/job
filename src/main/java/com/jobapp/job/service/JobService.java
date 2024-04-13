package com.jobapp.job.service;

import com.jobapp.job.dto.JobWithCompanyDto;
import com.jobapp.job.model.Job;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDto> findAll();
    Job findJobById(Long id);
    void createJob(Job job);
    Boolean deleteJob(Long id);
    Boolean updateJob(Long id, Job job);
}
