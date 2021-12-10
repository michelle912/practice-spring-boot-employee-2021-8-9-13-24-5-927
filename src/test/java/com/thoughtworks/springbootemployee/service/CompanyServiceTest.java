package com.thoughtworks.springbootemployee.service;


import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Company company = new Company("1", "spring");
        List<Company> companyList = Arrays.asList(company);

        // when
        doReturn(companyList).when(companyRepository).findAll();

        List<Company> actual = companyService.getAllCompanies();

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(companyList.get(0).getId(), actual.get(0).getId()),
                () -> assertEquals(companyList.get(0).getName(), actual.get(0).getName())
        );

    }

    @Test
    public void should_return_empty_list_when_getAllCompanies_given_no_companies() throws Exception {
        // given

        // when
        doReturn(Collections.emptyList()).when(companyRepository).findAll();

        List<Company> actual = companyService.getAllCompanies();

        // then
        assertTrue(actual.isEmpty());

    }


    @Test
    public void should_return_correct_companies_when_getCompany_given_id() throws Exception {
        // given
        String id = "1";
        Company company = new Company(id, "spring");
        company.setEmployees(Collections.singletonList(new Employee("1", "Lily1", 20, "Female", 8000, id)));

        // when
        doReturn(Optional.of(company)).when(companyRepository).findById(id);
        Company actual = companyService.getCompany(id);

        // then
        assertAll(
                () -> assertEquals(company.getId(), actual.getId()),
                () -> assertEquals(company.getName(), actual.getName()),
                () -> assertEquals(company.getEmployees().size(), actual.getEmployees().size()),
                () -> assertEquals(company.getEmployees().get(0), actual.getEmployees().get(0))
        );

    }

    @Test
    public void should_throw_exception_when_getCompany_given_company_not_exist() throws Exception {
        // given
        String id = "1";
        Company company = new Company(id, "spring");
        company.setEmployees(Collections.singletonList(new Employee("1", "Lily1", 20, "Female", 8000, id)));

        // when
        doReturn(Optional.empty()).when(companyRepository).findById(id);

        // then
        assertThrows(NoCompanyFoundException.class, () -> companyService.getCompany(id));

    }

    @Test
    public void should_get_all_employees_under_company_when_getAllEmployeesByCompanyId_given_id() throws Exception {
        // given
        String companyId1 = "1";
        String companyId2 = "2";
        Company company = new Company(companyId1, "spring");
        Employee employee1 = new Employee("1", "Lily1", 20, "Female", 8000,companyId1);
        Employee employee2 = new Employee("2", "Lily2", 20, "Female", 8000,companyId2);
        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        company.setEmployees(employeeList);

        // when
        doReturn(Arrays.asList(employee1)).when(employeeRepository).findAllByCompanyId(companyId1);

        List<Employee> actual = companyService.getAllEmployeesUnderCompany(companyId1);

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(employeeList.get(0), actual.get(0))
        );

    }

    @Test
    public void should_get_company_in_page_when_getCompanyByPage_given_page_and_pageSize() throws Exception {
        // given
        Integer page = 1;
        Integer pageSize = 2;
        Company company1 = new Company("1", "spring");
        Company company2 = new Company("2", "spring2");
        Company company3 = new Company("3", "spring3");
        List<Company> companyList = Arrays.asList(company1,company2, company3);
        Page<Company> companyPage = new PageImpl<>(Arrays.asList(company3));

        // when
        doReturn(companyPage).when(companyRepository).findAll(PageRequest.of(page, pageSize));

        List<Company> actual = companyService.getCompanyByPageAndPageSize(page, pageSize);

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(company3, actual.get(0))
        );
    }

    @Test
    public void should_return_empty_list_when_getCompanyByPage_given_invalid_page_and_pageSize() throws Exception {
        // given
        Integer invalidPage = 1;
        Integer pageSize = 2;

        // when
        doReturn(Page.empty()).when(companyRepository).findAll(PageRequest.of(invalidPage, pageSize));

        List<Company> actual = companyService.getCompanyByPageAndPageSize(invalidPage, pageSize);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void should_create_company_when_createCompany_given_company() throws Exception {
        // given
        Company incomingCompany = new Company("1", "spring");
        incomingCompany.setId(null);
        Company company = new Company("1", "spring");

        // when
        doReturn(company).when(companyRepository).save(incomingCompany);

        Company actual = companyService.createCompany(incomingCompany);

        // then
         assertEquals(company, actual);

    }

    @Test
    public void should_update_company_when_updateCompany_given_company() throws Exception {
        // given
        String id = "1";
        String newName = "spring2";
        Company existingCompany = new Company(id, "spring");
        Company input = new Company(id, newName);
        input.setId(null);
        Company updatedCompany = new Company(id, newName);


        // when
        doReturn(Optional.of(existingCompany)).when(companyRepository).findById(id);
        doReturn(updatedCompany).when(companyRepository).save(any(Company.class));

        Company actual = companyService.updateCompany(id, input);

        // then
        assertEquals(updatedCompany, actual);

    }

    @Test
    public void should_throw_exception_when_updateCompany_given_company_not_exist() throws Exception {
        // given
        String id = "1";
        String newName = "spring2";
        Company input = new Company(id, newName);
        input.setId(null);


        // when
        doReturn(Optional.empty()).when(companyRepository).findById(id);

        // then
        assertThrows(NoCompanyFoundException.class, () -> companyService.updateCompany(id, input));

    }

    @Test
    public void should_delete_company_with_id_when_deleteCompanyById_given_id() throws Exception {
        // given
        String id = "1";
        Company company = new Company(id, "spring");

        // when
        companyService.deleteCompany(id);

        // then
        Mockito.verify(companyRepository, times(1)).deleteById(id);

    }


}
