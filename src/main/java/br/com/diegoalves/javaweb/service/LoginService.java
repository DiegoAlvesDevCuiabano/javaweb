package br.com.diegoalves.javaweb.service;

import br.com.diegoalves.javaweb.repository.LoginRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    LoginRepository loginRepository;
    String loginBase = "admin";
    String senhaBase = "1234";
    public boolean validaLogin(String login, String senha) {
        return loginValido(login, senha);
    }
    public boolean loginValido(String login, String senha) {
        if(login.equals(loginBase) && senha.equals(senhaBase)) {
            return true;
        }
        return false;
    }
}
