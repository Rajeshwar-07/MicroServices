package com.embarkx.companyms.repository;


import com.embarkx.companyms.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
