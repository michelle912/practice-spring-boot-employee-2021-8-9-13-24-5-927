package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private List<Company> companyList;

    public CompanyRepository() {
        this.companyList = new ArrayList<>();
        Company company1 = new Company(1, "spring");
        company1.setEmployees(Collections.singletonList(new Employee(1, "Lily1", 20, "Female", 8000)));
        companyList.add(company1);
    }

    public List<Company> findAll() {
        return companyList;
    }

    public Company findById(Integer id) {
        return companyList.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployeeListById(Integer id) {
        return Optional.ofNullable(findById(id))
                .map(Company::getEmployees)
                .orElse(Collections.emptyList());
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyList.stream()
                .skip((long) page*pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        Integer nextId = companyList.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0)+1;
        company.setId(nextId);
        companyList.add(company);

        return company;
    }
}
