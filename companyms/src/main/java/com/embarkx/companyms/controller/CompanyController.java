package com.embarkx.companyms.controller;


import com.embarkx.companyms.entity.Company;
import com.embarkx.companyms.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAlllCompanies(){
        return  ResponseEntity.ok(companyService.allCompanies());
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@RequestBody Company company,@PathVariable Long id){
     companyService.updateCompany(company,id);
        return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        Company saveComapny = companyService.createCompany(company);
        return new ResponseEntity<>(saveComapny,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        boolean isDelete = companyService.deleteCompanyById(id);
        if(isDelete)
            return new ResponseEntity<>("Company successfully deleted",HttpStatus.OK);
        else
             return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id){
        Company company =  companyService.getCompanyById(id);
        if(company != null){
            return new ResponseEntity<>(company, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
