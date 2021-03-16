package br.com.zup.propostarefatorada.proposta.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analiseFinanceiraClient", url="${analiseFinanceiraClient.targetUrl}")
public interface AnaliseFinanceiraClient {

    @PostMapping("/api/solicitacao")
    public AnaliseFinanceiraResponse buscaPorAnaliseFinanceira(@RequestBody AnaliseFinanceiraRequest request);
}
