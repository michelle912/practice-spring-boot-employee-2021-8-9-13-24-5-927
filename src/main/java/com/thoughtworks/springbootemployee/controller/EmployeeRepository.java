package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private List<Employee> employeeList;

    public EmployeeRepository() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1,"Lily1", 20, "Female", 8000));
        employeeList.add(new Employee(2,"Lily2", 20, "Female", 8000));
        employeeList.add(new Employee(3,"Lily3", 20, "Female", 8000));
        employeeList.add(new Employee(4,"Lily4", 20, "Female", 8000));
        employeeList.add(new Employee(5,"Lily5", 20, "Female", 8000));


    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee findById(Integer id) {
        return employeeList.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeList.stream().skip((long) page*pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        Integer nextId = employeeList.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0)+1;
        employee.setId(nextId);
        employeeList.add(employee);

        return employee;
    }

    public Employee save(Employee employee) {
        Employee existingRecord = findById(employee.getId());

        employeeList.remove(existingRecord);
        employeeList.add(employee);
        return employee;
    }

    public void deleteById(Integer id) {
        Employee existingRecord = findById(id);

        employeeList.remove(existingRecord);
    }

}
