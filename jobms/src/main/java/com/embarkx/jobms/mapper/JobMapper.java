package com.embarkx.jobms.mapper;

import com.embarkx.jobms.dto.JobDTO;
import com.embarkx.jobms.entity.Job;
import com.embarkx.jobms.external.Company;
import com.embarkx.jobms.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(
            Job job,
            Company company,
            List<Review> reviews){
    JobDTO jobDTO = new JobDTO();
    jobDTO.setId(job.getId());
    jobDTO.setTitle(job.getTitle());
    jobDTO.setDescription(job.getDescription());
    jobDTO.setLocation(job.getLocation());
    jobDTO.setMaxSalary(job.getMaxSalary());
    jobDTO.setMinSalary(job.getMinSalary());
    jobDTO.setCompany(company);
    jobDTO.setReview(reviews);

    return jobDTO;
    }
 }
