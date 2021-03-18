package br.com.zup.propostarefatorada.cartao.integracao.bloqueio;

import br.com.zup.propostarefatorada.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private LocalDateTime bloqueadoEm = LocalDateTime.now();

    private String sistemaResponsavel;

    private boolean ativo;

    @ManyToOne
    private Cartao cartao;

    private String ipCliente;

    private String userAgent;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
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

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Bloqueio(String ipCliente, String sistemaResponsavel, String userAgent, Cartao cartao) {
        this.ipCliente = ipCliente;
        this.sistemaResponsavel = sistemaResponsavel;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.ativo = true;
    }

}