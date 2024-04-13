package com.jobapp.job.dto;

import com.jobapp.job.external.Company;
import com.jobapp.job.model.Job;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobWithCompanyDto {
    private Job job;
    private Company company;

}
