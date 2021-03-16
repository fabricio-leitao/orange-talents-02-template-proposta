package br.com.zup.propostarefatorada.cartao.integracao.vencimento;

import java.time.LocalDateTime;

public class VencimentoResponse {
    private final String id;
    private final Integer dia;
    private final LocalDateTime dataDeCriacao;

    public VencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Vencimento toModel(){
        return new Vencimento(id, dia, dataDeCriacao);
    }
}