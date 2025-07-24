package com.alexxlpz04.gestorclientes.controllers;

import com.alexxlpz04.gestorclientes.entities.Account;
import com.alexxlpz04.gestorclientes.services.AuthService;
import com.alexxlpz04.gestorclientes.ui.authForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("authForm", new authForm());
        return "auth/login";
    }

    @PostMapping("/dologin")
    public String dologin(Model model, HttpSession session, authForm authForm) {
        return this.authService.doLogin(model, session, authForm);
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        return "auth/register";
    }

    @PostMapping("/doregister")
    public String doregister(Model model, HttpSession session, Account account) {
        return this.authService.doRegister(model, session, account);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
