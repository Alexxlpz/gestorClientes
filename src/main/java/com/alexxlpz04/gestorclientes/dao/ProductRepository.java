package com.alexxlpz04.gestorclientes.dao;

import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.name like concat('%', :filtro,'%') and p.company.id = :companyid")
    public List<Product> filterByName(@Param("filtro") String filtro, @Param("companyid")Integer companyid);

    List<Product> findByCompanyId(Integer companyId);
}
