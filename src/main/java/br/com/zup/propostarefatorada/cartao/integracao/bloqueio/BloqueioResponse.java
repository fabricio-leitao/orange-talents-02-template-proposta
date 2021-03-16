package br.com.zup.propostarefatorada.cartao.integracao.bloqueio;

import br.com.zup.propostarefatorada.cartao.Cartao;

import java.time.LocalDateTime;

public class BloqueioResponse {

    private String id;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;
    private Cartao cartao;

    public BloqueioResponse(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo, Cartao cartao) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Bloqueio toModel(){
        return new Bloqueio(id, bloqueadoEm, sistemaResponsavel, ativo, cartao);
    }
}
