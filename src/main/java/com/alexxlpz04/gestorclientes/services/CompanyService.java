package com.alexxlpz04.gestorclientes.services;

import com.alexxlpz04.gestorclientes.dao.AppointmentRepository;
import com.alexxlpz04.gestorclientes.dao.CompanyRepository;
import com.alexxlpz04.gestorclientes.dao.RecordRepository;
import com.alexxlpz04.gestorclientes.entities.Appointment;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Record;
import com.alexxlpz04.gestorclientes.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RecordRepository recordRepository;

    public String companyList(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        return "company/company_list";
    }

    public String filterByName(Model model, HttpSession session, @RequestParam("filtro") String filtro) {

        model.addAttribute("companies", this.companyRepository.filterByName(filtro));
        return "company/company_list";
    }

    public String viewCompany(Model model, HttpSession session, int userid, int companyid) {

        List<Appointment> appointments;

        if (userid == -1) { // si ve la empresa sin ser usuario vera todas las citas de la empresa, asi reutilizaremos el metodo para que la empresa vea sus citas
            appointments = this.appointmentRepository.findByCompany(companyid);
        }else {
            appointments = this.appointmentRepository.findByUserAndCompany(userid, companyid);
        }


        Company company = this.companyRepository.findById(companyid).orElse(null);

        if(company == null) {
            model.addAttribute("error", "La empresa no existe");
            return "error";
        }

        String[] days = company.getDiasAbiertos().split(";");

        model.addAttribute("registered", this.recordRepository.isRegisteredUser(userid, companyid));
        model.addAttribute("days", days);
        model.addAttribute("company", company);
        model.addAttribute("appointments", appointments);
        return "company/view_company";
    }

    public String createRecord(Model model, HttpSession session, Integer companyid) {
        User user = (User) session.getAttribute("user");
        Company company = this.companyRepository.findById(companyid).orElse(null);
        Record record = new Record();
        if (company != null) {
            record.setCompany(company);
            record.setUser(user);
            record.setCompleted(false);
            this.recordRepository.save(record);
            model.addAttribute("message", "te has registrado correctamente como cliente de la empresa " + company.getCompanyName());
            return this.viewCompany(model, session, user != null ? user.getId() : -1, companyid);
        } else {
            model.addAttribute("error", "Company not found");
            return "error/error_page";
        }
    }
}
