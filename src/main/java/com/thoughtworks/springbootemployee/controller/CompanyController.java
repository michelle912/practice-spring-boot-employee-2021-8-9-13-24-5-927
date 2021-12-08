package com.thoughtworks.springbootemployee.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping()
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping(value="/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return companyRepository.findById(id);
    }

    @GetMapping(value="/{id}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        return companyRepository.findEmployeeListById(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPage(Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    @PostMapping()
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyRepository.create(company);
        return ResponseEntity.status(201).body(createdCompany);
    }

    @PutMapping(value="/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
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
    public ResponseEntity<String> deleteCompanyById(@PathVariable Integer id) {
        companyRepository.deleteById(id);
        return ResponseEntity.status(203).build();
    }
}
