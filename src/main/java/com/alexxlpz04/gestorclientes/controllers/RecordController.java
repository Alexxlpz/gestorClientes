package com.alexxlpz04.gestorclientes.controllers;

import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.services.RecordService;
import com.alexxlpz04.gestorclientes.ui.RecordForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/")
    public String listNonCompletedRecords(Model model, HttpSession session) {
        return this.recordService.listNonCompletedRecords(model, session);
    }

    @GetMapping("/viewRecord")
    public String viewRecord(Model model, HttpSession session, @RequestParam("recordid") Integer recordid) {
        Company company = (Company) session.getAttribute("company");
        return this.recordService.viewRecord(model, session, recordid, company.getId());
    }

    @PostMapping("/viewRecordCompleted")
    public String viewRecordCompleted(Model model, HttpSession session, @ModelAttribute("recordForm") RecordForm recordForm) {
        Company company = (Company) session.getAttribute("company");
        return this.recordService.viewRecordCompleted(model, session, recordForm, company.getId());
    }
}
