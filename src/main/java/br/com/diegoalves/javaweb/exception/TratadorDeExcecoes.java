package br.com.diegoalves.javaweb.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TratadorDeExcecoes {

    @ExceptionHandler(AplicacaoException.class)
    public String tratarErroDaAplicacao(AplicacaoException erro, Model model) {
        model.addAttribute("mensagem", erro.getMessage());
        return "erro";
    }

    @ExceptionHandler(DataAccessException.class)
    public String tratarErroDeBanco(DataAccessException erro, Model model) {
        model.addAttribute("mensagem", "Não foi possível acessar o banco de dados.");
        return "erro";
    }

    @ExceptionHandler(Exception.class)
    public String tratarErroInesperado(Exception erro, Model model) {
        model.addAttribute("mensagem", "Ocorreu um erro inesperado.");
        return "erro";
    }
}
