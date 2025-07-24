package com.alexxlpz04.gestorclientes.controllers;

import com.alexxlpz04.gestorclientes.entities.User;
import com.alexxlpz04.gestorclientes.services.CompanyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/list")
    public String list(Model model) {
        return this.companyService.companyList(model);
    }

    @PostMapping("/filterByName")
    public String filter(Model model, HttpSession session, @RequestParam("filtro") String filtro) {
        return this.companyService.filterByName(model, session, filtro);
    }

    @GetMapping("/view")
    public String viewCompany(Model model, HttpSession session, @RequestParam("companyid")int companyid) {
        User user = (User) session.getAttribute("user");
        int userid;

        if (user == null) {
            userid = -1;
        }else {
            userid = user.getId();
        }

        return this.companyService.viewCompany(model, session, userid, companyid);
    }

    @GetMapping("/createRecord")
    public String createRecord(Model model, HttpSession session, @RequestParam("companyid") Integer companyid) {
        return this.companyService.createRecord(model, session, companyid);
    }
}
