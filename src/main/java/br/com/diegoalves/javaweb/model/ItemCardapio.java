package br.com.diegoalves.javaweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_cardapio")
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incluido_por", nullable = false)
    private Usuario incluidoPor;

    protected ItemCardapio() {
    }

    public ItemCardapio(String nome, String descricao, BigDecimal preco, Usuario incluidoPor) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.incluidoPor = incluidoPor;
    }

    public void alterar(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
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

    public Usuario getIncluidoPor() {
        return incluidoPor;
    }
}
