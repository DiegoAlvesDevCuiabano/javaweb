package br.com.diegoalves.javaweb.dto;

import br.com.diegoalves.javaweb.model.ItemCardapio;

import java.math.BigDecimal;

public class ItemCardapioRespostaDTO {

    private final Long id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;

    public ItemCardapioRespostaDTO(ItemCardapio item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.descricao = item.getDescricao();
        this.preco = item.getPreco();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
