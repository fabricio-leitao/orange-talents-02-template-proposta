package br.com.zup.propostarefatorada.cartao;

import br.com.zup.propostarefatorada.cartao.biometria.Biometria;
import br.com.zup.propostarefatorada.cartao.integracao.bloqueio.Bloqueio;
import br.com.zup.propostarefatorada.cartao.integracao.carteira.Carteira;
import br.com.zup.propostarefatorada.cartao.integracao.parcela.Parcela;
import br.com.zup.propostarefatorada.cartao.integracao.renegociacao.Renegociacao;
import br.com.zup.propostarefatorada.cartao.integracao.vencimento.Vencimento;
import br.com.zup.propostarefatorada.proposta.Proposta;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<Bloqueio> bloqueios;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<Carteira> carteiras;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<Parcela> parcelas;
    private Integer limite;
    private Renegociacao renegociacao;
    private Vencimento vencimento;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private List<Biometria> biometrias;

    @OneToOne
    private Proposta proposta;

    @Enumerated(EnumType.STRING)
    private StatusCartao status = StatusCartao.ATIVO;

    @Deprecated
    public Cartao() {
    }


    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, List<Bloqueio> bloqueios, List<Parcela> parcelas, Integer limite, Renegociacao renegociacao, Vencimento vencimento, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.parcelas = parcelas;
        this.limite = limite;
        this.proposta = proposta;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public Integer getLimite() {
        return limite;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public Renegociacao getRenegociacao() {
        return renegociacao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public void toBloqueio(String ipCliente, String userAgent, String sistemaResponsavel) {
        Bloqueio bloqueio = new Bloqueio(ipCliente, userAgent, sistemaResponsavel, this);
        this.bloqueios.add(bloqueio);
    }

    public void updateStatus(StatusCartao status) throws  Exception{
        this.status = status;
    }

}