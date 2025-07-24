package com.alexxlpz04.gestorclientes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if(session.getAttribute("account") == null) {
            return "redirect:/auth/login";
        }else {
            model.addAttribute("account", session.getAttribute("account"));
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("company", session.getAttribute("company"));
            return "/home/home";
        }
    }
}
