package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private List<Company> companyList;

    @Autowired
    private EmployeeRepository employeeRepository;

    public CompanyRepository() {
        this.companyList = new ArrayList<>();
        Company company1 = new Company("1", "spring");
        Company company2 = new Company("2", "spring");
        Company company3 = new Company("3", "spring");
        Company company4 = new Company("4", "spring");
        Company company5 = new Company("5", "spring");
        Company company6 = new Company("6", "spring");
        companyList.addAll(Arrays.asList(company1, company2, company3, company4, company5, company6));
    }

    public List<Company> findAll() {
        return companyList;
    }

    public Company findById(String id) throws NoCompanyFoundException {
        Company existingCompany = companyList.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoCompanyFoundException("Company Not Found."));

        existingCompany.setEmployees(employeeRepository.aggregateByCompanyId(existingCompany.getId()));
        return existingCompany;
    }

    public List<Company> findByPage(Integer page, Integer pageSize) throws NoCompanyFoundException {
        List<Company> existingCompanyList = companyList.stream()
                .skip((long) page*pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        if (existingCompanyList.isEmpty()) {
            throw new NoCompanyFoundException("Company Not Found");
        }

        existingCompanyList.forEach(company -> company.setEmployees(employeeRepository.aggregateByCompanyId(company.getId())));

        return existingCompanyList;
    }

    public Company create(Company company) {
        Integer nextId = companyList.stream()
                .map(Company::getId)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0)+1;
        company.setId(String.valueOf(nextId));
        companyList.add(company);

        return company;
    }

    public Company save(Company company) throws NoCompanyFoundException {
        Company existingRecord = findById(company.getId());

        companyList.remove(existingRecord);
        companyList.add(company);
        return company;
    }

    public void deleteById(String id) throws NoCompanyFoundException {
        Company existingRecord = findById(id);

        companyList.remove(existingRecord);
    }

    public void clearAll(){
        companyList.clear();
    }


}
