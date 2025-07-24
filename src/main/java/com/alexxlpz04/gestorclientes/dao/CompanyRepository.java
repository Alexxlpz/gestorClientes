package com.alexxlpz04.gestorclientes.dao;

import com.alexxlpz04.gestorclientes.entities.Account;
import com.alexxlpz04.gestorclientes.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByAccount(Account account);

    // podriamos hacer uno que muestre cuando el numero de ocurrencias sea mayor al 80%
    @Query("select c from Company c where c.companyName like concat('%', :name,'%')")
    public List<Company> filterByName(@Param("name") String name);
}
