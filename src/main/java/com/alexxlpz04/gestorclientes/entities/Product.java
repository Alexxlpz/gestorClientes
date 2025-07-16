package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "`desc`", length = 500)
    private String desc;

    @Column(name = "prize", nullable = false)
    private Double prize;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company")
    private Company company;

}