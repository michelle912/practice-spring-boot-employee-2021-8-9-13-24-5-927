package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepositoryNew;


    public List<Employee> getAllEmployees() {
        return employeeRepositoryNew.findAll();
    }

    public Employee updateEmployee(String id, Employee employee) throws NoEmployeeFoundException {
        Employee existingRecord = employeeRepositoryNew
                .findById(id)
                .orElseThrow(() -> new NoEmployeeFoundException("Not Found."));

        if (employee == null) {
            return null;
        }

        if(employee.getSalary() != null) {
            existingRecord.setSalary(employee.getSalary());
        }

        if(employee.getAge() != null) {
            existingRecord.setAge(employee.getAge());
        }
        return employeeRepositoryNew.save(existingRecord);
    }

    public Employee getEmployee(String id) throws NoEmployeeFoundException {
        return employeeRepositoryNew.findById(id)
                .orElseThrow(() -> new NoEmployeeFoundException("Not Found."));
    }

    public List<Employee> getEmployeeWithGender(String gender) {
        return employeeRepositoryNew.findAllByGender(gender);
    }

    public List<Employee> getEmployeeFromPageAndPageSize(Integer page, Integer pageSize) {
        return employeeRepositoryNew.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepositoryNew.save(employee);
    }


    public void deleteEmployee(String id) {
        employeeRepositoryNew.deleteById(id);
    }
}
