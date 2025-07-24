package com.alexxlpz04.gestorclientes.services;

import com.alexxlpz04.gestorclientes.dao.RecordRepository;
import com.alexxlpz04.gestorclientes.entities.Company;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public String listNonCompletedRecords(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        if (company == null) {
            model.addAttribute("error", "No company found in session");
            return "error";
        }
        model.addAttribute("records", recordRepository.findByCompanyAndCompletedFalse(company.getId()));
        return "record/list_records";
    }
}
