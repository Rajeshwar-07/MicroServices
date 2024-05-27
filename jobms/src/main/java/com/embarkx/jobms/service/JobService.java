package com.embarkx.jobms.service;



import com.embarkx.jobms.dto.JobDTO;
import com.embarkx.jobms.entity.Job;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Job updateJob);
}
