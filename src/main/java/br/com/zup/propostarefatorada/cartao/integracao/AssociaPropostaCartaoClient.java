package br.com.zup.propostarefatorada.cartao.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "associaPropostaCartaoClient", url = "${associaPropostaCartaoClient.targetUrl}")
public interface AssociaPropostaCartaoClient {

    @PostMapping("/api/cartoes")
    CartaoAssociadoResponse associarPropostaCartao(@RequestBody @Valid CartaoAssociadoRequest request);

}