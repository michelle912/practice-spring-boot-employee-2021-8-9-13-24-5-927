package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_employee_when_findById_given_id() throws Exception {
        // given
        String id = "1";
        Employee employee = new Employee(id, "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        // when
        Optional<Employee> actual = employeeRepository.findById(id);

        // then
        assertEquals(employee, actual.get());
    }

    @Test
    public void should_return_all_employees_when_findAll_given_company_exist() throws Exception {
        // given
        String id = "1";
        Employee employee = new Employee(id, "Tom", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        // when
        List<Employee> actual = employeeRepository.findAll();

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(employee, actual.get(0))
        );
    }

    @Test
    public void should_return_employees_with_gender_when_findByGender_given_gender() throws Exception {
        // given
        String gender = "male";
        Employee employee = new Employee("1", "Tom", 20, gender, 10000,"1");
        employeeRepository.save(employee);

        // when
        List<Employee> actual = employeeRepository.findAllByGender(gender);

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(employee, actual.get(0))
        );
    }

    @Test
    public void should_return_employees_from_page_when_findByPage_given_page_and_pageSize() throws Exception {
        // given
        Integer page = 1;
        Integer pageSize = 2;
        Employee employee1 = new Employee("1", "Tom1", 20, "male", 10000,"1");
        Employee employee2 = new Employee("2", "Tom2", 20, "male", 10000,"1");
        Employee employee3 = new Employee("3", "Tom3", 20, "male", 10000,"1");
        Employee employee4 = new Employee("4", "Tom4", 20, "male", 10000,"1");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        // when
        List<Employee> actual = employeeRepository.findAll(pageRequest).getContent();

        // then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertEquals(employee3, actual.get(0)),
                () -> assertEquals(employee4, actual.get(1))
        );
    }

    @Test
    public void should_create_employees_when_create_given_employee() throws Exception {
        // given
        Employee employee1 = new Employee("1", "Tom1", 20, "male", 10000,"1");
        employee1.setId(null);

        // when
        employeeRepository.save(employee1);
        Optional<Employee> actual = employeeRepository.findById("1");

        // then
        assertAll(
                () -> assertNotNull(actual.get().getId()),
                () -> assertEquals(employee1.getName(), actual.get().getName()),
                () -> assertEquals(employee1.getGender(), actual.get().getGender()),
                () -> assertEquals(employee1.getAge(), actual.get().getAge()),
                () -> assertEquals(employee1.getSalary(), actual.get().getSalary()),
                () -> assertEquals(employee1.getCompanyId(), actual.get().getCompanyId())
        );
    }

    @Test
    public void should_save_employees_when_save_given_employee() throws Exception {
        // given
        Employee employee = new Employee("1", "Tom1", 20, "male", 10000,"1");
        employeeRepository.save(employee);

        Employee updatedEmployee = new Employee("1", "Tom2", 20, "male", 10000,"1");

        // when
        employeeRepository.save(updatedEmployee);
        Optional<Employee> actual = employeeRepository.findById("1");

        // then
        assertAll(
                () -> assertNotNull(actual.get().getId()),
                () -> assertEquals(updatedEmployee.getName(), actual.get().getName()),
                () -> assertEquals(updatedEmployee.getGender(), actual.get().getGender()),
                () -> assertEquals(updatedEmployee.getAge(), actual.get().getAge()),
                () -> assertEquals(updatedEmployee.getSalary(), actual.get().getSalary()),
                () -> assertEquals(updatedEmployee.getCompanyId(), actual.get().getCompanyId())
        );
    }

    @Test
    public void should_delete_employee_when_deleteById_given_id() throws Exception {
        // given
        Employee employee1 = new Employee("1", "Tom1", 20, "male", 10000, "1");
        Employee employee2 = new Employee("2", "Tom1", 20, "male", 10000, "1");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when
        employeeRepository.deleteById("1");

        List<Employee> actual = employeeRepository.findAll();

        // then
        assertEquals(1,actual.size());
    }

    @Test
    public void should_get_employee_with_company_id_when_aggregateByCompanyId_given_company_id() throws Exception {
        // given
        Employee employee1 = new Employee("1", "Tom1", 20, "male", 10000, "1");
        Employee employee2 = new Employee("2", "Tom1", 20, "male", 10000, "2");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when
        List<Employee> actual = employeeRepository.findAllByCompanyId("1");

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(employee1, actual.get(0))
        );
    }
}
