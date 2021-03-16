package br.com.zup.propostarefatorada.cartao.integracao.carteira;

import br.com.zup.propostarefatorada.cartao.Cartao;

import java.time.LocalDateTime;

public class CarteiraResponse {

    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;
    private Cartao cartao;

    public CarteiraResponse(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public Carteira toModel(){
        return new Carteira(id, email, associadaEm, emissor, cartao);
    }
}