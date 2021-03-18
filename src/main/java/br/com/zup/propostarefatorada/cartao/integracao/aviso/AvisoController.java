package br.com.zup.propostarefatorada.cartao.integracao.aviso;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.CartaoRepository;
import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AvisoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoRepository avisoRepository;

    @PostMapping("/api/cartoes/{id}/viagens")
    @Transactional
    public ResponseEntity<?> cadastrarViagem(@PathVariable String id, @Valid @RequestBody AvisoRequest request, HttpServletRequest httpRequest, @RequestHeader(HttpHeaders.USER_AGENT) String userAgent){

        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido!"));
        String ipCliente = httpRequest.getRemoteAddr();

        Aviso aviso = request.toModel(ipCliente, userAgent, cartao);
        cartao.viagemAgendada(aviso);
        avisoRepository.save(aviso);
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().build();
    }
}
