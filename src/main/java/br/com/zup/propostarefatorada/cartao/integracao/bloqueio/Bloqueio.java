package br.com.zup.propostarefatorada.cartao.integracao.bloqueio;

import br.com.zup.propostarefatorada.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String idExterno;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;


    @ManyToOne(fetch = FetchType.LAZY)
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String idExterno, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo, Cartao cartao) {
        this.idExterno = idExterno;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
        this.cartao = cartao;
    }

    public Cartao getCartao() {
        return cartao;
    }

}