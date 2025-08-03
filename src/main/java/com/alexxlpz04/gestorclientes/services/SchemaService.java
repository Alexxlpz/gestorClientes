package com.alexxlpz04.gestorclientes.services;

import com.alexxlpz04.gestorclientes.dao.CompanyRepository;
import com.alexxlpz04.gestorclientes.dao.SchemaRepository;
import com.alexxlpz04.gestorclientes.entities.Record;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Schema;
import com.alexxlpz04.gestorclientes.entities.User;
import com.alexxlpz04.gestorclientes.ui.SchemaForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class SchemaService {

    @Autowired
    private SchemaRepository schemaRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public String schemaList(Model model, int companyId) {
        List<Schema> schemas = schemaRepository.findByCompany(companyId);

        model.addAttribute("schemas", schemas);
        return "schema/schema_list";
    }

    public String addSchema(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        SchemaForm schemaForm = new SchemaForm();
        schemaForm.setCompanyId(company.getId());
        model.addAttribute("schemaForm", schemaForm);

        return "schema/add_schema";
    }

    public String guardarSchema(SchemaForm schemaForm, HttpSession session) {
        Schema schema = schemaForm.toSchema();
        Schema schemaSaved = new Schema();
        Company company = (Company) session.getAttribute("company");
        schema.setCompany(company);

        if(schema.getId() == 0) {
            schemaSaved.setName(schema.getName());
            schemaSaved.setType(schema.getType());
            schemaSaved.setMandatory(schema.getMandatory());
            schemaSaved.setCompany(schema.getCompany());
        }else {

            schemaSaved = this.schemaRepository.findById(schema.getId()).orElse(new Schema());
            schemaSaved.setName(schema.getName());
            schemaSaved.setType(schema.getType());
            schemaSaved.setMandatory(schema.getMandatory());
            schemaSaved.setCompany(schema.getCompany());
        }

        this.schemaRepository.save(schemaSaved);
        return "redirect:/schema/";
    }


    public String editSchema(Model model, Integer schemaid) {
        Schema schema = this.schemaRepository.findById(schemaid).orElse(null);
        if (schema != null) {
            SchemaForm schemaForm = new SchemaForm();
            schemaForm.cargarSchema(schema);
            model.addAttribute("schemaForm", schemaForm);
            return "schema/add_schema";
        } else {
            model.addAttribute("error", "Schema not found");
            return "error/error_page";
        }
    }

    public String removeSchema(Model model, Integer schemaid) {
        Schema schema = this.schemaRepository.findById(schemaid).orElse(null);
        if (schema != null) {
            this.schemaRepository.delete(schema);
            return "redirect:/schema/";
        } else {
            model.addAttribute("error", "Schema not found");
            return "error/error_page";
        }
    }
}
