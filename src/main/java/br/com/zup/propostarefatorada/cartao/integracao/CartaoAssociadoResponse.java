package br.com.zup.propostarefatorada.cartao.integracao;


import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.integracao.aviso.Aviso;
import br.com.zup.propostarefatorada.cartao.integracao.aviso.AvisoResponse;
import br.com.zup.propostarefatorada.cartao.integracao.bloqueio.Bloqueio;
import br.com.zup.propostarefatorada.cartao.integracao.bloqueio.BloqueioResponse;
import br.com.zup.propostarefatorada.cartao.integracao.carteira.Carteira;
import br.com.zup.propostarefatorada.cartao.integracao.carteira.CarteiraResponse;
import br.com.zup.propostarefatorada.cartao.integracao.parcela.Parcela;
import br.com.zup.propostarefatorada.cartao.integracao.parcela.ParcelaResponse;
import br.com.zup.propostarefatorada.cartao.integracao.renegociacao.Renegociacao;
import br.com.zup.propostarefatorada.cartao.integracao.renegociacao.RenegociacaoResponse;
import br.com.zup.propostarefatorada.cartao.integracao.vencimento.Vencimento;
import br.com.zup.propostarefatorada.cartao.integracao.vencimento.VencimentoResponse;
import br.com.zup.propostarefatorada.proposta.Proposta;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartaoAssociadoResponse {

    @JsonProperty("id")
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    private List<BloqueioResponse> bloqueios;
    private List<AvisoResponse> avisos;
    private List<CarteiraResponse> carteiras;
    private List<ParcelaResponse> parcelas;
    private Integer limite;
    private String idProposta;
    private RenegociacaoResponse renegociacao;
    private VencimentoResponse vencimento;

    public CartaoAssociadoResponse(String numeroCartao, LocalDateTime emitidoEm, String titular, List<BloqueioResponse> bloqueios, List<AvisoResponse> avisos, List<CarteiraResponse> carteiras, List<ParcelaResponse> parcelas, Integer limite, String idProposta, RenegociacaoResponse renegociacao, VencimentoResponse vencimento) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.idProposta = idProposta;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<BloqueioResponse> getBloqueios() {
        return bloqueios;
    }

    public List<AvisoResponse> getAvisos() {
        return avisos;
    }

    public List<CarteiraResponse> getCarteiras() {
        return carteiras;
    }

    public List<ParcelaResponse> getParcelas() {
        return parcelas;
    }

    public Integer getLimite() {
        return limite;
    }

    public RenegociacaoResponse getRenegociacao() {
        return renegociacao;
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public Cartao toModel(Proposta proposta) {

        List<Bloqueio> bloqueios = new ArrayList<>();
        List<Aviso> avisos = new ArrayList<>();
        List<Carteira> carteiras = new ArrayList<>();
        List<Parcela> parcelas = new ArrayList<>();

        this.bloqueios.forEach(bloqueioResponse -> bloqueios.add(bloqueioResponse.toModel()));
        this.avisos.forEach(avisoResponse -> avisos.add(avisoResponse.toModel()));
        this.carteiras.forEach(carteiraResponse -> carteiras.add(carteiraResponse.toModel()));
        this.parcelas.forEach(parcelaResponse -> parcelas.add(parcelaResponse.toModel()));

        Renegociacao renegociacao = this.renegociacao == null ? null : this.renegociacao.toModel();
        Vencimento vencimento = this.vencimento == null ? null : this.vencimento.toModel();


        return new Cartao(numeroCartao, emitidoEm, titular, bloqueios, avisos, carteiras, parcelas, limite, renegociacao, vencimento, proposta);
    }
}
