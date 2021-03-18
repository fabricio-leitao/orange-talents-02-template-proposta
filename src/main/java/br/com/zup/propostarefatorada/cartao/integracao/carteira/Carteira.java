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

    @Column(updatable = false)
    private LocalDateTime associadaEm = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private CarteiraDigital emissor;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String idExterno, String email, CarteiraDigital emissor, Cartao cartao) {
        this.idExterno = idExterno;
        this.email = email;
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

    public CarteiraDigital getEmissor() {
        return emissor;
    }

    public Cartao getCartao() {
        return cartao;
    }
}