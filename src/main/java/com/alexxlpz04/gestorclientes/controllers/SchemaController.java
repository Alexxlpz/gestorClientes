package com.alexxlpz04.gestorclientes.controllers;

import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Schema;
import com.alexxlpz04.gestorclientes.services.SchemaService;
import com.alexxlpz04.gestorclientes.ui.SchemaForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/schema")
public class SchemaController {

    @Autowired
    private SchemaService schemaService;

    @GetMapping("/")
    public String schemaList(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        return this.schemaService.schemaList(model, company.getId());
    }

    @GetMapping("/addSchema")
    public String addSchema(Model model, HttpSession session) {
        return this.schemaService.addSchema(model, session);
    }

    @PostMapping("/doAddSchema")
    public String doAddSchema(SchemaForm schemaForm, HttpSession session) {
        return this.schemaService.guardarSchema(schemaForm, session);
    }

    @GetMapping("/editSchema")
    public String editSchema(Model model, @RequestParam("schemaid") Integer schemaid) {
        return this.schemaService.editSchema(model, schemaid);
    }

    @PostMapping("/doEditSchema")
    public String doEditSchema(SchemaForm schemaForm, HttpSession session) {
        return this.schemaService.guardarSchema(schemaForm, session);
    }

    @GetMapping("/removeSchema")
    public String removeSchema(Model model, @RequestParam("schemaid") Integer schemaid) {
        return this.schemaService.removeSchema(model, schemaid);
    }
}
