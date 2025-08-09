package com.alexxlpz04.gestorclientes.controllers;

import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String listProduct(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        return this.productService.productList(model, company.getId());
    }

    @PostMapping("/filterByName")
    public String filter(Model model, HttpSession session, @RequestParam("filtro") String filtro) {
        Company company = (Company) session.getAttribute("company");
        return this.productService.filterByName(model, session, filtro, company.getId());
    }
}
