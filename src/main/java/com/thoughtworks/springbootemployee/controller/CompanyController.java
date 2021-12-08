package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping()
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping(value="/{id}")
    public Company getCompanyById(@PathVariable Integer id) throws NoCompanyFoundException {
        return companyRepository.findById(id);
    }

    @GetMapping(value="/{id}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        return employeeRepository.aggregateByCompanyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPage(Integer page, Integer pageSize) throws NoCompanyFoundException {
        return companyRepository.findByPage(page, pageSize);
    }

    @PostMapping()
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyRepository.create(company);
        return ResponseEntity.status(201).body(createdCompany);
    }

    @PutMapping(value="/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) throws NoCompanyFoundException {
        Company existingReord = companyRepository.findById(id);

        if (existingReord == null || company == null) {
            return null;
        }

        if(company.getCompanyName() != null) {
            existingReord.setCompanyName(company.getCompanyName());
        }
        return companyRepository.save(existingReord);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Integer id) throws NoCompanyFoundException {
        companyRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
