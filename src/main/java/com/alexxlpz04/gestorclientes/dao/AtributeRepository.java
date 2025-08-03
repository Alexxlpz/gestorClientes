package com.alexxlpz04.gestorclientes.dao;

import com.alexxlpz04.gestorclientes.entities.Atribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AtributeRepository extends JpaRepository<Atribute, Integer> {
    @Query("SELECT a FROM Atribute a WHERE a.record.id = :recordid AND a.scheme.id = :schemaid")
    public Atribute findByRecordIdAndSchemaId(@Param("recordid")Integer recordid, @Param("schemaid")Integer schemaid);
}
