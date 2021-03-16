package br.com.zup.propostarefatorada.cartao.integracao.vencimento;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Vencimento {

    private String idVencimento;
    private Integer diaVencimento;
    private LocalDateTime vencimentoCriadoEm;


    public Vencimento(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.idVencimento = id;
        this.diaVencimento = dia;
        this.vencimentoCriadoEm = dataDeCriacao;
    }

    @Deprecated
    public Vencimento() {

    }
}