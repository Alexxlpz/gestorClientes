package com.alexxlpz04.gestorclientes.dao;

import com.alexxlpz04.gestorclientes.entities.Appointment;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> user(User user);

    @Query("select c from Appointment c where c.user.id = :userid and c.company.id = :companyid")
    public List<Appointment> findByUserAndCompany(@Param("userid")int userid, @Param("companyid")int companyid);

    @Query("select c from Appointment c where c.company.id = :companyid")
    public List<Appointment> findByCompany(@Param("companyid")int companyid);
}
