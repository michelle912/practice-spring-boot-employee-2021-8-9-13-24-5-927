package com.thoughtworks.springbootemployee.service;


import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void should_get_all_companies_when_getAllCompanies_given_companies() throws Exception {
        // given
        Company company = new Company(1, "spring");
        company.setEmployees(Collections.singletonList(new Employee(1, "Lily1", 20, "Female", 8000,1)));
        List<Company> companyList = Arrays.asList(company);

        // when
        doReturn(companyList).when(companyRepository).findAll();

        List<Company> actual = companyService.getAllCompanies();

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(companyList.get(0).getId(), actual.get(0).getId()),
                () -> assertEquals(companyList.get(0).getCompanyName(), actual.get(0).getCompanyName()),
                () -> assertEquals(companyList.get(0).getEmployees().size(), actual.get(0).getEmployees().size()),
                () -> assertEquals(companyList.get(0).getEmployees().get(0), actual.get(0).getEmployees().get(0))
        );

    }

    @Test
    public void should_return_correct_companies_when_getAllCompanies_given_id() throws Exception {
        // given
        Integer id = 1;
        Company company = new Company(id, "spring");
        company.setEmployees(Collections.singletonList(new Employee(1, "Lily1", 20, "Female", 8000, id)));

        // when
        doReturn(company).when(companyRepository).findById(id);
        Company actual = companyService.getCompany(id);

        // then
        assertAll(
                () -> assertEquals(company.getId(), actual.getId()),
                () -> assertEquals(company.getCompanyName(), actual.getCompanyName()),
                () -> assertEquals(company.getEmployees().size(), actual.getEmployees().size()),
                () -> assertEquals(company.getEmployees().get(0), actual.getEmployees().get(0))
        );

    }

    @Test
    public void should_get_all_employees_under_company_when_getAllEmployeesByCompanyId_given_id() throws Exception {
        // given
        Integer companyId1 = 1;
        Integer companyId2 = 2;
        Company company = new Company(companyId1, "spring");
        Employee employee1 = new Employee(1, "Lily1", 20, "Female", 8000,companyId1);
        Employee employee2 = new Employee(2, "Lily2", 20, "Female", 8000,companyId2);
        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        company.setEmployees(employeeList);

        // when
        doReturn(employeeList).when(employeeRepository).aggregateByCompanyId(companyId1);

        List<Employee> actual = companyService.getAllEmployeesUnderCompany(companyId1);

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(employeeList.get(0), actual.get(0))
        );

    }

}
