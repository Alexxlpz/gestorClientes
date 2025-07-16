package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user", nullable = false, length = 100)
    private String user;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "surname", length = 100)
    private String surname;

}