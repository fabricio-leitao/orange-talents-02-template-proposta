package br.com.zup.propostarefatorada.cartao.integracao.aviso;

import br.com.zup.propostarefatorada.cartao.Cartao;

import java.time.LocalDateTime;

public class AvisoResponse {

    private LocalDateTime validoAte;
    private String destino;
    private Cartao cartao;

    public LocalDateTime getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Aviso toModel(){
        return new Aviso(validoAte, destino, cartao);
    }
}
