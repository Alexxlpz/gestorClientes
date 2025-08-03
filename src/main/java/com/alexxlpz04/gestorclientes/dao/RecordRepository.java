package com.alexxlpz04.gestorclientes.dao;

import com.alexxlpz04.gestorclientes.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {

    @Query("SELECT r FROM Record r WHERE r.company.id = :companyid AND r.completed = false")
    public List<Record> findByCompanyAndCompletedFalse(@Param("companyid")Integer companyid);

    @Query("SELECT true FROM Record r WHERE r.user.id = :userid AND r.company.id = :companyid")
    public Boolean isRegisteredUser(@Param("userid")Integer userid, @Param("companyid")Integer companyid);
}
