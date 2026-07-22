package br.com.diegoalves.javaweb.controller;


import br.com.diegoalves.javaweb.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    LoginService loginService = new LoginService();
    @GetMapping("/")
    public String login(String login, String senha) {
        return "login";
    }
    @PostMapping("/login")
    public String validaLogin(@RequestParam String usuario, @RequestParam String senha) {
        boolean loginValido = loginService.validaLogin(usuario, senha);
        if (loginValido) {
            return "/Home";
        }
        return "login";
    }
}
