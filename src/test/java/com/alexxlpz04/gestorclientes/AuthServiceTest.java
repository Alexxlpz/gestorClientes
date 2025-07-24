package com.alexxlpz04.gestorclientes;

import com.alexxlpz04.gestorclientes.dao.AccountRepository;
import com.alexxlpz04.gestorclientes.dao.CompanyRepository;
import com.alexxlpz04.gestorclientes.dao.UserRepository;
import com.alexxlpz04.gestorclientes.entities.Account;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.User;
import com.alexxlpz04.gestorclientes.services.AuthService;
import com.alexxlpz04.gestorclientes.ui.authForm;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private Model model;
    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthService authService;

    @Nested
    @DisplayName("Tests for doLogin method")
    class DoLoginTests {

        @Test
        @DisplayName("Test login with non-existent user with valid account")
        void testLoginWithNonExistentUserForAValidAccount() {
            // Arrange
            authForm form = mock(authForm.class);
            when(form.getUsername()).thenReturn("user1");
            when(form.getPassword()).thenReturn("pass1");
            when(form.isCompany()).thenReturn(false);

            Account account = new Account();
            account.setUser("user1");
            account.setPassword("pass1");
            when(accountRepository.findByUser("user1")).thenReturn(account);

            when(userRepository.findByAccount(account)).thenReturn(null);

            // Act
            String result = authService.doLogin(model, session, form);

            // Assert
            assertEquals("auth/login", result);
            verify(model).addAttribute("error", "No tienes un usuario asociada con esta cuenta");
        }

        @Test
        @DisplayName("Test login with non-existent company with a valid account")
        void testLoginWithNonExistentCompanyForAValidAccount() {
            // Arrange
            authForm form = mock(authForm.class);
            when(form.getUsername()).thenReturn("user1");
            when(form.getPassword()).thenReturn("pass1");
            when(form.isCompany()).thenReturn(true);

            Account account = new Account();
            account.setUser("user1");
            account.setPassword("pass1");
            when(accountRepository.findByUser("user1")).thenReturn(account);

            when(companyRepository.findByAccount(account)).thenReturn(null);

            // Act
            String result = authService.doLogin(model, session, form);

            // Assert
            assertEquals("auth/login", result);
            verify(model).addAttribute("error", "No tienes una empresa asociada con esta cuenta");
        }

        @Test
        @DisplayName("Test login with incorrect password")
        void testLoginWithIncorrectPassword() {
            // Arrange
            authForm form = mock(authForm.class);
            when(form.getUsername()).thenReturn("user1");
            when(form.getPassword()).thenReturn("wrongpass");

            Account account = new Account();
            account.setUser("user1");
            account.setPassword("correctpass");
            when(accountRepository.findByUser("user1")).thenReturn(account);

            // Act
            String result = authService.doLogin(model, session, form);

            // Assert
            assertEquals("auth/login", result);
            verify(model).addAttribute("error", "Contraseña incorrecta");
        }

        @Test
        @DisplayName("Test login with incorrect user")
        void testLoginWithIncorrectUser() {
            // Arrange
            authForm form = mock(authForm.class);
            when(form.getUsername()).thenReturn("wronguser1");


            when(accountRepository.findByUser("wronguser1")).thenReturn(null);

            // Act
            String result = authService.doLogin(model, session, form);

            // Assert
            assertEquals("auth/login", result);
            verify(model).addAttribute("error", "Usuario incorrecto");
        }

        @Test
        @DisplayName("Test successful login as user")
        void testSuccessfulLoginAsUser() {
            // Arrange
            authForm form = mock(authForm.class);
            when(form.getUsername()).thenReturn("user1");
            when(form.getPassword()).thenReturn("pass1");
            when(form.isCompany()).thenReturn(false);

            Account account = new Account();
            account.setUser("user1");
            account.setPassword("pass1");
            when(accountRepository.findByUser("user1")).thenReturn(account);

            User user = new User();
            when(userRepository.findByAccount(account)).thenReturn(user);

            // Act
            String result = authService.doLogin(model, session, form);

            // Assert
            assertEquals("redirect:/", result);
            verify(session).setAttribute("account", account);
            verify(session).setAttribute("user", user);
        }

        @Test
        @DisplayName("Test successful login as company")
        void testSuccessfulLoginAsCompany() {
            // Arrange
            authForm form = mock(authForm.class);
            when(form.getUsername()).thenReturn("user1");
            when(form.getPassword()).thenReturn("pass1");
            when(form.isCompany()).thenReturn(true);

            Account account = new Account();
            account.setUser("user1");
            account.setPassword("pass1");
            when(accountRepository.findByUser("user1")).thenReturn(account);

            Company company = new Company();
            when(companyRepository.findByAccount(account)).thenReturn(company);

            // Act
            String result = authService.doLogin(model, session, form);

            // Assert
            assertEquals("redirect:/", result);
            verify(session).setAttribute("account", account);
            verify(session).setAttribute("company", company);
        }
    }


    @Nested
    @DisplayName("Tests for doRegister method")
    class DoRegisterTests {

        @Test
        @DisplayName("Test regsiter with existent userName")
        void testRegisterWithExistentUserName() {
            // Arrange
            Account account = new Account();
            account.setUser("user1");
            account.setPassword("pass1");
            account.setName("user1");
            account.setSurname("surname1");

            when(accountRepository.findByUser("user1")).thenReturn(account);

            // Act
            String result = authService.doRegister(model, session, account);

            // Assert
            assertEquals("auth/register", result);
            verify(model).addAttribute("error", "El usuario ya existe");
        }

        @Test
        @DisplayName("Test register with new userName")
        void testRegisterSuscessful() {
            // Arrange
            Account account = new Account();
            account.setUser("user1");
            account.setPassword("pass1");
            account.setName("user1");
            account.setSurname("surname1");

            when(accountRepository.findByUser("user1")).thenReturn(null).thenReturn(account);
            // Act
            String result = authService.doRegister(model, session, account);

            // Assert
            assertEquals("redirect:/", result);
            verify(session).setAttribute("account", account);
            verify(session).setAttribute(eq("user"), any(User.class));
        }
    }
}