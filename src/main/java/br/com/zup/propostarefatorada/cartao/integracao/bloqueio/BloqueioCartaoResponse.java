package br.com.zup.propostarefatorada.cartao.integracao.bloqueio;

import br.com.zup.propostarefatorada.cartao.StatusCartao;

public class BloqueioCartaoResponse {

    private String resultado;

    @Deprecated
    public BloqueioCartaoResponse() {
    }

    public BloqueioCartaoResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public StatusCartao toStatusCartao(){
        if(resultado.equals("BLOQUEADO")){
            return StatusCartao.BLOQUEADO;
        }
        return StatusCartao.ATIVO;
    }
}
