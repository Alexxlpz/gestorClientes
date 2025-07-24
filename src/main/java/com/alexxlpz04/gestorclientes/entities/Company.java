package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Company {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "companyName", length = 100)
    private String companyName;

    @Column(name = "horaInicio", length = 100)
    private String horaInicio;

    @Column(name = "horaFin", length = 100)
    private String horaFin;

    @Column(name = "diasAbiertos", length = 100)
    private String diasAbiertos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyType")
    private CompanyType companyType;

}