package com.embarkx.companyms.service.impl;


import com.embarkx.companyms.entity.Company;
import com.embarkx.companyms.repository.CompanyRepository;
import com.embarkx.companyms.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> allCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
       Optional<Company> companyOptional = companyRepository.findById(id);
    if(companyOptional.isPresent()){
        Company existingCompany = companyOptional.get();
        existingCompany.setName(company.getName());
        existingCompany.setDescription(company.getDescription());
        companyRepository.save(existingCompany);
        return true;
    }
        return false;
    }

    @Override
    public Company createCompany(Company company) {
       return companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
