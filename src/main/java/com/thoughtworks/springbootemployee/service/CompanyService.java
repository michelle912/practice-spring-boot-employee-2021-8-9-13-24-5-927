package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public Company getCompany(String id) throws NoCompanyFoundException {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NoCompanyFoundException("Not Found."));
    }

    public List<Employee> getAllEmployeesUnderCompany(String id) {
        return employeeRepository.findAllByCompanyId(id);
    }

    public List<Company> getCompanyByPageAndPageSize(Integer page, Integer pageSize)  {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(String id, Company company) throws NoCompanyFoundException {
        Company existingRecord = companyRepository.findById(id)
                .orElseThrow(() -> new NoCompanyFoundException("Not Found."));

        if (company == null) {
            return null;
        }

        if(company.getName() != null) {
            existingRecord.setName(company.getName());
        }
        return companyRepository.save(existingRecord);
    }

    public void deleteCompany(String id){
        companyRepository.deleteById(id);
    }
}
