package br.com.diegoalves.javaweb.exception;

public abstract class AplicacaoException extends RuntimeException {

    protected AplicacaoException(String mensagem) {
        super(mensagem);
    }
}
