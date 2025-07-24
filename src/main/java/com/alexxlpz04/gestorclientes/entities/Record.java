package com.alexxlpz04.gestorclientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "record", uniqueConstraints = {
        @UniqueConstraint(name = "Record_UNIQUE", columnNames = {"company"}),
        @UniqueConstraint(name = "Record_UNIQUE_1", columnNames = {"user"})
})
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company", nullable = false)
    private Company company;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ColumnDefault("0")
    @Column(name = "completed")
    private Boolean completed;

}