package com.alexxlpz04.gestorclientes.ui;

import com.alexxlpz04.gestorclientes.entities.Record;
import lombok.Data;
import java.util.List;

@Data
public class RecordForm {
    private Record record;
    private List<SchemaAtributePair> atributes;
}
