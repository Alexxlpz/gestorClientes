package com.alexxlpz04.gestorclientes.services;

import com.alexxlpz04.gestorclientes.dao.AtributeRepository;
import com.alexxlpz04.gestorclientes.dao.RecordRepository;
import com.alexxlpz04.gestorclientes.dao.SchemaRepository;
import com.alexxlpz04.gestorclientes.entities.Atribute;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Record;
import com.alexxlpz04.gestorclientes.entities.Schema;
import com.alexxlpz04.gestorclientes.ui.RecordForm;
import com.alexxlpz04.gestorclientes.ui.SchemaAtributePair;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private SchemaRepository schemaRepository;

    @Autowired
    private AtributeRepository atributesRepository;

    public String listNonCompletedRecords(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        if (company == null) {
            model.addAttribute("error", "No company found in session");
            return "error";
        }
        model.addAttribute("records", recordRepository.findByCompanyAndCompletedFalse(company.getId()));
        return "record/list_records";
    }

    public String viewRecord(Model model, HttpSession session, Integer recordid, Integer companyid) {
        if (recordid == null) {
            model.addAttribute("error", "Ha habido un error al intentar ver el registro");
            return "error";
        }
        List<Schema> schemas = schemaRepository.findByCompany(companyid);
        if (schemas.isEmpty()) {
            return "redirect:/record/";
        }else {
            Record record = recordRepository.findById(recordid).orElse(null);
            if(record == null) {
                return "redirect:/record/";
            }

            RecordForm recordForm = new RecordForm();
            recordForm.setRecord(record);
            List<SchemaAtributePair> pairList = new ArrayList<>();

            for (Schema schema : schemas) {
                Atribute atribute = atributesRepository.findByRecordIdAndSchemaId(schema.getId(), record.getId());
                SchemaAtributePair pair = new SchemaAtributePair();
                pair.setSchema(schema);

                if (atribute == null) {
                    atribute = new Atribute();
                    atribute.setRecord(record);
                    atribute.setScheme(schema);
                    atribute.setValue("");
                }
                pair.setAtribute(atribute);
                pairList.add(pair);
            }

            recordForm.setAtributes(pairList);
            model.addAttribute("recordForm", recordForm);
            return "record/write_record";
        }
    }

    public String viewRecordCompleted(Model model, HttpSession session, RecordForm recordForm, Integer companyid) {
        if (recordForm.getRecord() == null || recordForm.getRecord().getId() == null) {
            model.addAttribute("error", "Ha habido un error al intentar ver el registro");
            return "error";
        }
        List<Schema> schemas = schemaRepository.findByCompany(companyid);
        if (schemas.isEmpty()) {
            return "redirect:/record/";
        } else {
            Record record = recordRepository.findById(recordForm.getRecord().getId()).orElse(null);
            if (record == null) {
                return "redirect:/record/";
            }

            for (SchemaAtributePair schemaAtributePair: recordForm.getAtributes()) {
                Atribute atribute = schemaAtributePair.getAtribute();
                if (atribute == null) {
                    model.addAttribute("error", "Ha habido un error al guardar la ficha");
                    return "error";
                }

                this.atributesRepository.save(atribute);
            }

            record.setCompleted(true);
            this.recordRepository.save(record);
            return "redirect:/record/";
        }
    }
}
