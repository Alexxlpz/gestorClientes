package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Record", uniqueConstraints = {
        @UniqueConstraint(name = "Record_UNIQUE_1", columnNames = {"user"}),
        @UniqueConstraint(name = "Record_UNIQUE", columnNames = {"company"})
})
public class Record {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company", nullable = false)
    private Company company;

}