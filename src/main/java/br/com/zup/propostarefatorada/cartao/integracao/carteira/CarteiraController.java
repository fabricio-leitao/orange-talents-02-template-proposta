package br.com.zup.propostarefatorada.cartao.integracao.carteira;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.CartaoRepository;
import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CarteiraController {

    @Autowired
    private CarteiraRepository carteiraRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private AssociaPropostaCartaoClient associaPropostaCartaoClient;

    @PostMapping("/api/cartoes/{id}/carteiras")
    public ResponseEntity<?> cadastra(@PathVariable String id,
                                      @Valid @RequestBody CarteiraRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {

        if (carteiraRepository.existsByEmissor(request.getCarteira())) {
            return ResponseEntity.unprocessableEntity().body("Carteira já está cadastrada");
        }

        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        try {
            CarteiraResponse carteiraResponse = associaPropostaCartaoClient.adicionarCarteira(cartao.getNumeroCartao(), request);
            Carteira carteira = request.toModel(cartao, carteiraResponse.getId());
            if(carteiraResponse.getResultado().equals("ASSOCIADA")) {
                carteiraRepository.save(carteira);
                URI uri = uriComponentsBuilder.path("/api/carteiras/" + id + "{id}").buildAndExpand(carteira.getId()).toUri();
                return ResponseEntity.created(uri).build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro de criação");
            }
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Carteira já se encontra cadastrada");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor. ");
        }

    }
}
