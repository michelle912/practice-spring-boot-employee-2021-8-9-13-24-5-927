package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(String id, Employee employee) throws NoEmployeeFoundException {
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

    public Employee getEmployee(String id) throws NoEmployeeFoundException {
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


    public void deleteEmployee(String id) throws NoEmployeeFoundException {
        employeeRepository.deleteById(id);
    }
}
