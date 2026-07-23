package br.com.diegoalves.javaweb.exception;

public class LoginException extends AplicacaoException {

    public LoginException() {
        super("Não foi possível identificar o usuário autenticado.");
    }
}
