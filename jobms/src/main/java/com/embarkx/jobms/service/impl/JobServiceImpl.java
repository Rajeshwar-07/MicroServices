package com.embarkx.jobms.service.impl;


import com.embarkx.jobms.clients.CompanyClient;
import com.embarkx.jobms.clients.ReviewClient;
import com.embarkx.jobms.dto.JobDTO;
import com.embarkx.jobms.entity.Job;
import com.embarkx.jobms.external.Company;
import com.embarkx.jobms.external.Review;
import com.embarkx.jobms.mapper.JobMapper;
import com.embarkx.jobms.repository.JobRepository;
import com.embarkx.jobms.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {


    private JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;

    private ReviewClient reviewClient;

    int attempt = 0;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient){
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }


    @Override
//    @CircuitBreaker(name="companyBreaker",
//            fallbackMethod="companyBreakerFallback")
//    @Retry(name="companyBreaker",
//            fallbackMethod="companyBreakerFallback")
    @RateLimiter(name="companyBreaker",
            fallbackMethod="companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt: "+ ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }


    private JobDTO convertToDto(Job job){
//    Company company = restTemplate.getForObject(
//            "http://COMPANYMS:8081/companies/"+job.getCompanyId(),
//            Company.class);
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
//        ResponseEntity<List<Review>> reviewResponse =  restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//          List<Review> reviews = reviewResponse.getBody();
            JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company,reviews);
//            jobDTO.setCompany(company);
            return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
       Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }


    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }


    @Override
    public boolean updateJob(Long id, Job updateJob) {
       Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()){
                Job job = jobOptional.get();
                job.setTitle(updateJob.getTitle());
                job.setDescription(updateJob.getDescription());
                job.setMinSalary(updateJob.getMinSalary());
                job.setMaxSalary(updateJob.getMaxSalary());
                job.setLocation(updateJob.getLocation());
                jobRepository.save(job);
                return true;
            }
        return false;
    }
}
