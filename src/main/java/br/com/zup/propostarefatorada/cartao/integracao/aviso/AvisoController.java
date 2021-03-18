package br.com.zup.propostarefatorada.cartao.integracao.aviso;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.CartaoRepository;
import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import feign.FeignException;
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

    @Autowired
    private AssociaPropostaCartaoClient associaPropostaCartaoClient;

    @PostMapping("/api/cartoes/{id}/viagens")
    @Transactional
    public ResponseEntity<?> cadastrarViagem(@PathVariable String id, @RequestHeader(HttpHeaders.USER_AGENT) String userAgent, @RequestBody @Valid AvisoRequest request, HttpServletRequest httpRequest){

        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido!"));
        String numeroCartao = cartao.getNumeroCartao();
        String ipCliente = httpRequest.getRemoteAddr();

        Aviso aviso = request.toModel(ipCliente, userAgent, cartao);

        try{
            AvisoResponse avisoResponse = associaPropostaCartaoClient.notificarViagem(numeroCartao, request);

            if(avisoResponse.getResultado().equals("CRIADO")) {
                avisoRepository.save(aviso);
            } else {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro de criação");
            }
        }catch (FeignException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aviso já existe");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno de servidor");
        }
        return ResponseEntity.ok().build();
    }

}
