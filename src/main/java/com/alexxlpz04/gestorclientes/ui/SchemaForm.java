package com.alexxlpz04.gestorclientes.ui;

import com.alexxlpz04.gestorclientes.entities.Schema;
import lombok.Data;

@Data
public class SchemaForm {
    private int id;
    private String name;
    private String type;
    private boolean mandatory;
    private int companyId;

    public void cargarSchema(Schema schema) {
        this.id = schema.getId();
        this.name = schema.getName();
        this.type = schema.getType();
        this.mandatory = schema.getMandatory() != null ? schema.getMandatory() : false;
        this.companyId = schema.getCompany() != null ? schema.getCompany().getId() : 0;
    }

    public Schema toSchema() {
        Schema schema = new Schema();
        schema.setId(this.id);
        schema.setName(this.name);
        schema.setType(this.type);
        schema.setMandatory(this.mandatory);
        // Assuming Company is set elsewhere, as it requires a Company object
        return schema;
    }
}
