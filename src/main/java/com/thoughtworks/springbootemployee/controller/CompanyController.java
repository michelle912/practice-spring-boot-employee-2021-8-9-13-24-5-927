package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping()
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping(value="/{id}")
    public Company getCompanyById(@PathVariable Integer id) throws NoCompanyFoundException {
        return companyService.getCompany(id);
    }

    @GetMapping(value="/{id}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        return companyService.getAllEmployeesUnderCompany(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPage(Integer page, Integer pageSize) throws NoCompanyFoundException {
        return companyService.getCompanyByPageAndPageSize(page, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping(value="/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) throws NoCompanyFoundException {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id) throws NoCompanyFoundException {
        companyService.deleteCompany(id);
    }
}
