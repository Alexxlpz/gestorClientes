package com.alexxlpz04.gestorclientes.services;

import com.alexxlpz04.gestorclientes.dao.ProductRepository;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String productList(Model model, Integer companyid) {
        List<Product> products = this.productRepository.findByCompanyId(companyid);
        model.addAttribute("products", products);
        return "product/product_list";
    }

    public String filterByName(Model model, HttpSession session, String filtro, Integer companyid) {
        model.addAttribute("products", this.productRepository.filterByName(filtro, companyid));
        return "product/product_list";
    }

}
