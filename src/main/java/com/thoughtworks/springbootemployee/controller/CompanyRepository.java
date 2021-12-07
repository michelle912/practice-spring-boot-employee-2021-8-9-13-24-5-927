package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
