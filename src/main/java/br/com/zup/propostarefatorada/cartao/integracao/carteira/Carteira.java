package br.com.zup.propostarefatorada.cartao.integracao.carteira;

import br.com.zup.propostarefatorada.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String idExterno;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String id, String email, LocalDateTime associadaEm, String emissor, Cartao cartao) {
        this.idExterno = idExterno;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }

    public Cartao getCartao() {
        return cartao;
    }
}