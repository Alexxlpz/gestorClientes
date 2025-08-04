package com.alexxlpz04.gestorclientes;

import com.alexxlpz04.gestorclientes.dao.AppointmentRepository;
import com.alexxlpz04.gestorclientes.dao.CompanyRepository;
import com.alexxlpz04.gestorclientes.dao.RecordRepository;
import com.alexxlpz04.gestorclientes.entities.*;
import com.alexxlpz04.gestorclientes.entities.Record;
import com.alexxlpz04.gestorclientes.services.CompanyService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompanyServiceTest {

    @Nested
    @DisplayName("UNIT test for companyList method")
    class companyListTest {

        @Mock
        CompanyRepository companyRepository;

        @InjectMocks
        CompanyService companyService;

        @Test
        @DisplayName("the companies are listed correctly")
        public void companyList_listedCorrectly_returnAllTheCompaniesInModel() {
            //Arrange
            List<Company> companies = new ArrayList<>();
            companies.add(new Company());
            companies.add(new Company());
            when(companyRepository.findAll()).thenReturn(companies);

            Model model = mock(Model.class);

            //Act
            String result = this.companyService.companyList(model);
            //Assert
            assertEquals("company/company_list", result);
            verify(model, times(1)).addAttribute("companies", companies);
        }

        @Test
        @DisplayName("there arent any comopany in db")
        public void companyList_NoCompaniesInDataBase_returnNoCompany() {
            //Arrange
            when(companyRepository.findAll()).thenReturn(null);

            Model model = mock(Model.class);

            //Act
            String result = this.companyService.companyList(model);
            //Assert
            assertEquals("company/company_list", result);
            verify(model, times(1)).addAttribute("companies", null);
        }
    }

    @Nested
    @DisplayName("UNIT test for companyfilter method")
    class companyfilterByNameTest {


        @Mock
        CompanyRepository companyRepository2;

        @InjectMocks
        CompanyService companyService;

        @Autowired
        CompanyRepository companyRepository;

//        @Test
//        @DisplayName("prove that the filter method in repository run correctly")
//        public void companyfilterByName_RposotoryMethodRunCorrectly() {
//            //Arrange
//            List<Company> companies = this.companyRepository.findAll();
//            List<Company> filteredCompanies = this.companyRepository.filterByName("c");
//
//            String filter = "c";
//
//            int numCompanies = 0;
//            for(Company comp : companies) {
//                if(comp.getCompanyName().equals(filter)) {
//                    numCompanies++;
//                }
//            }
//            int numFilteredCompanies = 0;
//            for(Company comp : filteredCompanies) {
//                if(comp.getCompanyName().equals(filter)) {
//                    numFilteredCompanies++;
//                }
//            }
//            //Act
//            //Assert
//            assertEquals(numCompanies, numFilteredCompanies);
//        }

        @Test
        @DisplayName("the companies are filter correctly, will be listed all the companies wich contains the filter in its name")
        public void companyList_filterCorrectly_returnAllTheCompaniesWhichContainsFilter() {
            //Arrange
            HttpSession session = mock(HttpSession.class);
            List<Company> companies = new ArrayList<>();
            companies.add(new Company());
            companies.add(new Company());
            when(companyRepository2.filterByName("something")).thenReturn(companies);

            Model model = mock(Model.class);

            //Act
            String result = this.companyService.filterByName(model, session, "something");
            //Assert
            assertEquals("company/company_list", result);
            verify(model, times(1)).addAttribute("companies", companies);
        }
    }

    @Nested
    @DisplayName("UNIT test for viewCompany method")
    class viewCompanyTest {

        @Mock
        CompanyRepository companyRepository;

        @Mock
        AppointmentRepository appointmentRepository;

        @Mock
        RecordRepository recordRepository;

        @InjectMocks
        CompanyService companyService;

        @Mock
        HttpSession session;

        @Test
        @DisplayName("the company is viewed correctly")
        public void viewCompany_companyViewedCorrectly_returnCompanyView() {
            //Arrange
            Model model = mock(Model.class);
            int userid = 1;
            int companyid = 1;
            Company company = new Company();
            company.setDiasAbiertos("Monday;Tuesday;Wednesday");
            when(companyRepository.findById(companyid)).thenReturn(Optional.of(company));

            List<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment());
            appointments.add(new Appointment());
            when(appointmentRepository.findByUserAndCompany(userid, companyid)).thenReturn(appointments);


            //Act
            String result = this.companyService.viewCompany(model, session, userid, companyid);
            //Assert
            assertEquals("company/view_company", result);
            verify(model, times(1)).addAttribute("days", new String[]{"Monday", "Tuesday", "Wednesday"});
            verify(model, times(1)).addAttribute("company", company);
            verify(model, times(1)).addAttribute("appointments", appointments);
        }

        @Test
        @DisplayName("user is -1, so the company will see all the appointments of the company")
        public void viewCompany_notUserRequest_returnCompanyViewWithAllCompanyAppointments() {
            //Arrange
            Model model = mock(Model.class);
            int userid = -1;
            int companyid = 1;
            Company company = new Company();
            company.setDiasAbiertos("Monday;Tuesday;Wednesday");
            when(companyRepository.findById(companyid)).thenReturn(Optional.of(company));

            List<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment());
            appointments.add(new Appointment());
            when(appointmentRepository.findByCompany(companyid)).thenReturn(appointments);


            //Act
            String result = this.companyService.viewCompany(model, session, userid, companyid);
            //Assert
            assertEquals("company/view_company", result);
            verify(model, times(1)).addAttribute("days", new String[]{"Monday", "Tuesday", "Wednesday"});
            verify(model, times(1)).addAttribute("company", company);
            verify(model, times(1)).addAttribute("appointments", appointments);
        }

        @Test
        @DisplayName("companyid is not found, so it will return an error")
        public void viewCompany_companyidIsWrong_returnErrorPage() {
            //Arrange
            Model model = mock(Model.class);
            int userid = 1;
            int companyid = -1;
            when(companyRepository.findById(companyid)).thenReturn(Optional.empty());

            List<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment());
            appointments.add(new Appointment());
            when(appointmentRepository.findByUserAndCompany(userid, companyid)).thenReturn(appointments);


            //Act
            String result = this.companyService.viewCompany(model, session, userid, companyid);
            //Assert
            assertEquals("error", result);
            verify(model, times(1)).addAttribute("error", "La empresa no existe");
        }
    }

    @Nested
    @DisplayName("UNIT test for createRecord method")
    public class createRecordTest {

        @Mock
        CompanyRepository companyRepository;

        @Mock
        RecordRepository recordRepository;

        @Mock
        HttpSession session;

        @Mock
        AppointmentRepository appointmentRepository;

        @InjectMocks
        CompanyService companyService;

        @Test
        @DisplayName("user successfully registers as a client of the company")
        public void createRecord_userRegistersSuccessfully_returnCompanyView() {
            // Arrange
            when(session.getAttribute("user")).thenReturn(new User());
            when(companyRepository.findById(1)).thenReturn(Optional.of(new Company()));
            Model model = mock(Model.class);
            int companyid = 1;
            User user = new User();
            user.setId(1);
            Company company = new Company();
            company.setCompanyName("Test Company");
            company.setDiasAbiertos("Monday;Tuesday;Wednesday");
            when(companyRepository.findById(companyid)).thenReturn(Optional.of(company));

            List<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment());
            appointments.add(new Appointment());
            when(appointmentRepository.findByUserAndCompany(user.getId(), companyid)).thenReturn(appointments);

            when(session.getAttribute("user")).thenReturn(user);
            when(companyRepository.findById(companyid)).thenReturn(Optional.of(company));

            //Act
            String result = companyService.createRecord(model, session, companyid);

            // Assert
            assertEquals("company/view_company", result);
            verify(recordRepository, times(1)).save(any(Record.class));
            verify(model, times(1)).addAttribute("message", "te has registrado correctamente como cliente de la empresa Test Company");
        }

        @Test
        @DisplayName("company not found, returns error page")
        public void createRecord_companyNotFound_returnErrorPage() {
            Model model = mock(Model.class);
            int companyid = -1;

            when(companyRepository.findById(companyid)).thenReturn(Optional.empty());

            String result = companyService.createRecord(model, session, companyid);

            assertEquals("error/error_page", result);
            verify(model, times(1)).addAttribute("error", "Company not found");
            verify(recordRepository, never()).save(any(Record.class));
        }
    }

}
