package br.com.zup.propostarefatorada.cartao.integracao.renegociacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RenegociacaoResponse {
    private final Long id;
    private final Integer quantidade;
    private final BigDecimal valor;
    private final LocalDateTime dataDeCriacao;

    public RenegociacaoResponse(Long id, Integer quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Renegociacao toModel() {
        return new Renegociacao(id, quantidade, valor, dataDeCriacao);
    }
}