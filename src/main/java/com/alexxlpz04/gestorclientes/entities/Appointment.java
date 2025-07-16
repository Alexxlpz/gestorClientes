package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "`desc`", length = 500)
    private String desc;

    @Column(name = "hour", length = 100)
    private String hour;

    @ColumnDefault("0")
    @Column(name = "completed")
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

}