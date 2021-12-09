package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private List<Employee> employeeList;

    public EmployeeRepository() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee("1","Lily1", 20, "Female", 8000,"1"));
        employeeList.add(new Employee("2","Lily2", 20, "Female", 8000,"2"));
        employeeList.add(new Employee("3","Lily3", 20, "Female", 8000,"3"));
        employeeList.add(new Employee("4","Lily4", 20, "Female", 8000,"4"));
        employeeList.add(new Employee("5","Lily5", 20, "Female", 8000,"5"));
        employeeList.add(new Employee("6","Tom", 20, "male", 8000,"6"));

    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee findById(String id) throws NoEmployeeFoundException {
        return employeeList.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow( () -> new NoEmployeeFoundException("Employee not found"));
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeList.stream().skip((long) page*pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        Integer nextId = employeeList.stream()
                .map(Employee::getId)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0)+1;
        employee.setId(String.valueOf(nextId));
        employeeList.add(employee);

        return employee;
    }

    public Employee save(Employee employee) throws NoEmployeeFoundException {
        Employee existingRecord = findById(employee.getId());

        employeeList.remove(existingRecord);
        employeeList.add(employee);
        return employee;
    }

    public void deleteById(String id) throws NoEmployeeFoundException {
        Employee existingRecord = findById(id);

        employeeList.remove(existingRecord);
    }

    public void clearAll() {
        employeeList.clear();
    }

    public List<Employee> aggregateByCompanyId(String id) {

        return employeeList.stream()
                .filter(employee -> employee.getCompanyId().equals(id))
                .collect(Collectors.toList());

    }
}
