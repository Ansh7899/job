package com.jobapp.job.service;

import com.jobapp.job.dto.JobDto;
import com.jobapp.job.model.Job;

import java.util.List;

public interface JobService {
    List<JobDto> findAll();
    JobDto findJobById(Long id);
    void createJob(Job job);
    Boolean deleteJob(Long id);
    Boolean updateJob(Long id, Job job);
}
