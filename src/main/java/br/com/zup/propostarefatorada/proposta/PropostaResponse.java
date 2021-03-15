package br.com.zup.propostarefatorada.proposta;

public class PropostaResponse {

    private String id;
    private StatusProposta status;

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.status = proposta.getStatus();
    }

    public String getId() {
        return id;
    }

    public StatusProposta getStatus() {
        return status;
    }
}
