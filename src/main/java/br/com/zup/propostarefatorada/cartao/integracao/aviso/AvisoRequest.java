package br.com.zup.propostarefatorada.cartao.integracao.aviso;

import br.com.zup.propostarefatorada.cartao.Cartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoRequest {

    @NotBlank
    private String destino;
    @Future
    private LocalDate validoAte;

    public AvisoRequest(@NotBlank String destino, @Future LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public Aviso toModel(String ipCliente, String userAgent, Cartao cartao) {
        return new Aviso(validoAte, destino, cartao, ipCliente, userAgent);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
