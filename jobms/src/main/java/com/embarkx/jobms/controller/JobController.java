package com.embarkx.jobms.controller;


import com.embarkx.jobms.dto.JobDTO;
import com.embarkx.jobms.entity.Job;
import com.embarkx.jobms.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job added successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO =  jobService.getJobById(id);
    if(jobDTO != null)
        return new ResponseEntity<>(jobDTO,HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean job = jobService.deleteJobById(id);
        if(job)
            return new ResponseEntity<>("Success",HttpStatus.OK);
        return new ResponseEntity<>("Resource Not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
//    @RequestMapping(value="/jobs/{id}",method = RequestMethod.PUT)
    public ResponseEntity<String> updateMethod(@PathVariable Long id, @RequestBody Job updatedJob){
        boolean updated = jobService.updateJob(id,updatedJob);
        if(updated){
            return new ResponseEntity<>("Job Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}