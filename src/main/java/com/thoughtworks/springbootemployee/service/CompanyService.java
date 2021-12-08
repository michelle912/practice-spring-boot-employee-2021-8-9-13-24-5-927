package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompany(Integer id) throws NoCompanyFoundException {
        return companyRepository.findById(id);
    }

    public List<Employee> getAllEmployeesUnderCompany(Integer companyId1) {
        return null;
    }
}
