package br.com.zup.propostarefatorada.cartao.integracao;

import br.com.zup.propostarefatorada.cartao.integracao.aviso.AvisoRequest;
import br.com.zup.propostarefatorada.cartao.integracao.aviso.AvisoResponse;
import br.com.zup.propostarefatorada.cartao.integracao.bloqueio.BloqueioCartaoResponse;
import br.com.zup.propostarefatorada.cartao.integracao.bloqueio.BloqueioRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "associaPropostaCartaoClient", url = "${associaPropostaCartaoClient.targetUrl}")
public interface AssociaPropostaCartaoClient {

    @PostMapping("/api/cartoes")
    CartaoAssociadoResponse associarPropostaCartao(@RequestBody @Valid CartaoAssociadoRequest request);

    @PostMapping("api/cartoes/{id}/bloqueios")
    BloqueioCartaoResponse bloquearCartao(@PathVariable String id, BloqueioRequest request);

    @PostMapping("/api/cartoes/{id}/avisos")
    AvisoResponse notificarViagem(@PathVariable String id, AvisoRequest request);
}
