package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "companyType")
public class CompanyType {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "companyType", nullable = false, length = 100)
    private String companyType;

}