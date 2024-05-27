package com.embarkx.companyms.service;


import com.embarkx.companyms.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> allCompanies();

    boolean updateCompany(Company company,Long id);

    Company createCompany(Company company);

    boolean deleteCompanyById(long id);

    Company getCompanyById(Long id);
}


