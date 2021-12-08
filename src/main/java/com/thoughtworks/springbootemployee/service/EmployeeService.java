package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Integer id, Employee employee) throws NoEmployeeFoundException {
        Employee existingRecord = employeeRepository.findById(id);

        if (existingRecord == null || employee == null) {
            return null;
        }

        if(employee.getSalary() != null) {
            existingRecord.setSalary(employee.getSalary());
        }

        if(employee.getAge() != null) {
            existingRecord.setAge(employee.getAge());
        }
        return employeeRepository.save(existingRecord);
    }

    public Employee getEmployee(Integer id) throws NoEmployeeFoundException {
        return employeeRepository.findById(id);
    }

    public List<Employee> getEmployeeWithGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getEmployeeFromPageAndPageSize(Integer page, Integer pageSize) {
        return employeeRepository.findByPage(page, pageSize);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.create(employee);
    }


    public void deleteEmployee(Integer id) throws NoEmployeeFoundException {
        employeeRepository.deleteById(id);
    }
}
