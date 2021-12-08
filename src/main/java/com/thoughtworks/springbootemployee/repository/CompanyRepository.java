package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private List<Company> companyList;

    public CompanyRepository() {
        this.companyList = new ArrayList<>();
        Company company1 = new Company(1, "spring");
        Company company2 = new Company(2, "spring");
        Company company3 = new Company(3, "spring");
        Company company4 = new Company(4, "spring");
        Company company5 = new Company(5, "spring");
        Company company6 = new Company(6, "spring");
        companyList.addAll(Arrays.asList(company1, company2, company3, company4, company5, company6));
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

    public Company save(Company company) {
        Company existingRecord = findById(company.getId());

        companyList.remove(existingRecord);
        companyList.add(company);
        return company;
    }

    public void deleteById(Integer id) {
        Company existingRecord = findById(id);

        companyList.remove(existingRecord);
    }

    public void clearAll(){
        companyList.clear();
    }


}
