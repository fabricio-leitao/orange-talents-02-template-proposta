package br.com.zup.propostarefatorada.cartao.integracao.parcela;

import br.com.zup.propostarefatorada.cartao.Cartao;

import java.math.BigDecimal;

public class ParcelaResponse {

    private String id;
    private Integer quantidade;
    private BigDecimal valor;
    private Cartao cartao;

    public ParcelaResponse(String id, Integer quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Parcela toModel(){
        return new Parcela(id, quantidade, valor, cartao);
    }
}