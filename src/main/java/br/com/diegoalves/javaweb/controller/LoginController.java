package br.com.diegoalves.javaweb.controller;


import br.com.diegoalves.javaweb.model.Usuario;
import br.com.diegoalves.javaweb.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarLogin(@RequestParam String usuario,
                               @RequestParam String senha,
                               HttpSession session,
                               Model model) {
        Usuario usuarioAutenticado = loginService.autenticar(usuario, senha);

        if (usuarioAutenticado == null) {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
            return "login";
        }

        session.setAttribute("usuarioId", usuarioAutenticado.getId());
        session.setAttribute("usuarioLogin", usuarioAutenticado.getLogin());

        return "redirect:/home";
    }

}
