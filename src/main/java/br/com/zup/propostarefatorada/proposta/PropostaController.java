package br.com.zup.propostarefatorada.proposta;

import br.com.zup.propostarefatorada.exception.ErroPadronizado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @Autowired
    private PropostaRepository repository;

    @PostMapping(value = "/api/propostas")
    @Transactional
    public ResponseEntity<?> criarProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uri){

        if(repository.existsByDocumento(request.getDocumento())){
            return ResponseEntity.unprocessableEntity().body(new ErroPadronizado(Arrays.asList("Este documento já possui uma proposta atrelada.")));
        }

        Proposta proposta = request.toModel();
        repository.save(proposta);

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
}
