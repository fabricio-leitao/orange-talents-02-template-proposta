package br.com.zup.propostarefatorada.cartao.integracao.parcela;

import br.com.zup.propostarefatorada.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Parcela {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String idExterno;
    private Integer quantidade;
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cartao cartao;

    @Deprecated
    public Parcela(){}

    public Parcela(String idExterno, Integer quantidade, BigDecimal valor, Cartao cartao) {
        this.idExterno = idExterno;
        this.quantidade = quantidade;
        this.valor = valor;
        this.cartao = cartao;
    }
}
