package br.com.zup.propostarefatorada.cartao.integracao.bloqueio;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.CartaoRepository;
import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class BloqueioController {

    private static final Logger log = LoggerFactory
            .getLogger(BloqueioController.class);

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private AssociaPropostaCartaoClient associaPropostaCartaoClient;

    @Transactional
    @PostMapping("/api/cartoes/{id}/bloqueado")
    public ResponseEntity<?> bloqueiaCartao(@PathVariable String id,@RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                            @Valid @RequestBody BloqueioRequest request,
                                            HttpServletRequest servletRequest) {

        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        String numeroCartao = cartao.getNumeroCartao();

        String ipCliente = servletRequest.getRemoteAddr();

        try {
            BloqueioCartaoResponse bloqueioCartaoResponse = associaPropostaCartaoClient.bloquearCartao(numeroCartao, request);
            if (bloqueioCartaoResponse.getResultado().equals("BLOQUEADO")) {
                cartao.toBloqueio(ipCliente, userAgent, request.getSistemaResponsavel());
                cartao.updateStatus(bloqueioCartaoResponse.toStatusCartao());
                cartaoRepository.save(cartao);
            }
        } catch (FeignException e) {
            return ResponseEntity.unprocessableEntity().body("Cartão já está bloqueado");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno de servidor!!Tente Novamente");
        }

        return ResponseEntity.ok().body("Cartão bloqueado com Sucesso!");
    }
}
