package com.alexxlpz04.gestorclientes.ui;

import com.alexxlpz04.gestorclientes.entities.Atribute;
import com.alexxlpz04.gestorclientes.entities.Schema;
import lombok.Data;

@Data
public class SchemaAtributePair {
    private Schema schema;
    private Atribute atribute;
}
