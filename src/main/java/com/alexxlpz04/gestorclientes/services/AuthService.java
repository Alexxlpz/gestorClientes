package com.alexxlpz04.gestorclientes.services;

import com.alexxlpz04.gestorclientes.dao.AccountRepository;
import com.alexxlpz04.gestorclientes.dao.CompanyRepository;
import com.alexxlpz04.gestorclientes.dao.UserRepository;
import com.alexxlpz04.gestorclientes.entities.Account;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.User;
import com.alexxlpz04.gestorclientes.ui.authForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public String doLogin(Model model, HttpSession session, authForm authForm) {

        Account account = this.accountRepository.findByUser(authForm.getUsername());

        if (account == null) {
            model.addAttribute("error", "Usuario incorrecto");

        } else if(account.getPassword().equals(authForm.getPassword()) && account.getUser().equals(authForm.getUsername())) {

            if(authForm.isCompany()){

                Company empresa = this.companyRepository.findByAccount(account);
                if(empresa == null){
                    model.addAttribute("error", "No tienes una empresa asociada con esta cuenta");
                }else {
                    session.setAttribute("account", account);
                    session.setAttribute("company", empresa);
                    return "redirect:/";
                }
            }else {
                User user = this.userRepository.findByAccount(account);
                if(user == null){
                    model.addAttribute("error", "No tienes un usuario asociada con esta cuenta");
                }else {
                    session.setAttribute("account", account);
                    session.setAttribute("user", user);
                    return "redirect:/";
                }
            }

        } else if (!account.getPassword().equals(authForm.getPassword()) && account.getUser().equals(authForm.getUsername())) {
            model.addAttribute("error", "Contraseña incorrecta");

        }

        return "auth/login";
    }

    public String doRegister(Model model, HttpSession session, Account account) {

        Account accountAux = this.accountRepository.findByUser(account.getUser());

        if (accountAux == null) {
//            Account accountToSave = new Account();
//            accountToSave.setUser(account.getUser());
//            accountToSave.setPassword(account.getPassword());
//            accountToSave.setName(account.getName());
//            accountToSave.setSurname(account.getSurname());

            this.accountRepository.save(account);
            account = this.accountRepository.findByUser(account.getUser());

            User user = new User();
            user.setAccount(account);
            user.setAccount1(account.getId());

            this.userRepository.save(user);
            session.setAttribute("account", account);
            session.setAttribute("user", user);
            return "redirect:/";
        }else {
            model.addAttribute("error", "El usuario ya existe");
        }

        return "auth/register";
    }
}
