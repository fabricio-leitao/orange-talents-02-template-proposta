package br.com.zup.propostarefatorada.proposta.integracao;

import br.com.zup.propostarefatorada.proposta.StatusProposta;

public class AnaliseFinanceiraResponse {

    private String documento;
    private String nome;
    private String resultadoSolicitacao;
    private String idProposta;

    public AnaliseFinanceiraResponse(String documento, String nome, String id, String status) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = id;
        this.resultadoSolicitacao = status;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public StatusProposta toModel() {

        if("COM_RESTRICAO".equals(resultadoSolicitacao)){
            return StatusProposta.NAO_ELEGIVEL;
        }
        return StatusProposta.ELEGIVEL;
    }
}
