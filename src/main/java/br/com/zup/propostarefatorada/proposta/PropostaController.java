package br.com.zup.propostarefatorada.proposta;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.CartaoRepository;
import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoRequest;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoResponse;
import br.com.zup.propostarefatorada.exception.ErroPadronizado;
import br.com.zup.propostarefatorada.proposta.integracao.AnaliseFinanceiraClient;
import br.com.zup.propostarefatorada.proposta.integracao.AnaliseFinanceiraRequest;
import br.com.zup.propostarefatorada.proposta.integracao.AnaliseFinanceiraResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class PropostaController {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AnaliseFinanceiraClient analiseFinanceiraClient;

    @Autowired
    private AssociaPropostaCartaoClient associaPropostaCartaoClient;

    private List<Proposta> propostas = new ArrayList<>();

    @PostMapping(value = "/api/propostas")
    @Transactional
    public ResponseEntity<?> criarProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uri){

        if(repository.existsByDocumento(request.getDocumento())){
            return ResponseEntity.unprocessableEntity().body(new ErroPadronizado(Arrays.asList("Este documento já possui uma proposta atrelada.")));
        }

        Proposta proposta = request.toModel();
        repository.save(proposta);
        StatusProposta status = enviaParaAnalise(proposta);
        proposta.updateStatus(status);

        if(proposta.getStatus() == StatusProposta.ELEGIVEL){
            propostas.add(proposta);
        }

        URI location = uri.path("/api/propostas/{id}")
                .buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/api/propostas/{idProposta}")
    public ResponseEntity<?> encontraPropostaPeloid(@PathVariable @NotBlank String idProposta){
        Optional<Proposta> proposta = repository.findById(idProposta);

        if(proposta.get() == null){
            return ResponseEntity.badRequest().body(new ErroPadronizado(Arrays.asList("Proposta não encontrada!")));
        }


        //Corrigido
        return ResponseEntity.ok(new PropostaResponse(proposta.get()));
        //return ResponseEntity.ok(proposta);
    }

    private StatusProposta enviaParaAnalise(Proposta proposta) {

        try{
            AnaliseFinanceiraRequest req = new AnaliseFinanceiraRequest(proposta);
            AnaliseFinanceiraResponse response = analiseFinanceiraClient.buscaPorAnaliseFinanceira(req);
            return response.toModel();
        } catch (FeignException.UnprocessableEntity e){
            return StatusProposta.NAO_ELEGIVEL;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Um Erro inesperado aconteceu!");
        }
    }


}
