package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping()
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping(value="/{id}")
    public CompanyResponse getCompanyById(@PathVariable String id) throws NoCompanyFoundException {
        return companyMapper.toResponse(companyService.getCompany(id));
    }

    @GetMapping(value="/{id}/employees")
    public List<EmployeeResponse> getAllEmployeesByCompanyId(@PathVariable String id) throws NoCompanyFoundException {
        return companyService.getAllEmployeesUnderCompany(id)
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPage(Integer page, Integer pageSize) throws NoCompanyFoundException {
        return companyService.getCompanyByPageAndPageSize(page, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.createCompany(companyMapper.toEntity(companyRequest));
    }

    @PutMapping(value="/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody CompanyRequest companyRequest) throws NoCompanyFoundException {
        return companyService.updateCompany(id, companyMapper.toEntity(companyRequest));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable String id) throws NoCompanyFoundException {
        companyService.deleteCompany(id);
    }
}
