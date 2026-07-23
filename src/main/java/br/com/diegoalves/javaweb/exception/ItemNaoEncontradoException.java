package br.com.diegoalves.javaweb.exception;

public class ItemNaoEncontradoException extends AplicacaoException {

    public ItemNaoEncontradoException() {
        super("Item do cardápio não encontrado.");
    }
}
