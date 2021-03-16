package br.com.zup.propostarefatorada.cartao.integracao;

import br.com.zup.propostarefatorada.proposta.Proposta;

public class CartaoAssociadoRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public CartaoAssociadoRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public CartaoAssociadoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
