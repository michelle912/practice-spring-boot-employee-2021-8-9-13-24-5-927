package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompany(Integer id) throws NoCompanyFoundException {
        return companyRepository.findById(id);
    }

    public List<Employee> getAllEmployeesUnderCompany(Integer id) {
        return employeeRepository.aggregateByCompanyId(id);
    }

    public List<Company> getCompanyByPageAndPageSize(Integer page, Integer pageSize) throws NoCompanyFoundException {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company createCompany(Company company) {
        return null;
    }
}
