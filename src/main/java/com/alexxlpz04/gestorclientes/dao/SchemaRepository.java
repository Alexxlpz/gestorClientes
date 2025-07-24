package com.alexxlpz04.gestorclientes.dao;

import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchemaRepository extends JpaRepository<Schema, Integer> {

    @Query("select s from Schema s where s.company.id = :companyid")
    public List<Schema> findByCompany(@Param("companyid") int companyid);
}
