package com.thoughtworks.springbootemployee.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private String id;
    private String name;
    private List<Employee> employees;

    public Company() {
    }

    public Company(String id, String companyName) {
        this.id = id;
        this.name = companyName;
        this.employees = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(employees, company.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employees);
    }
}
