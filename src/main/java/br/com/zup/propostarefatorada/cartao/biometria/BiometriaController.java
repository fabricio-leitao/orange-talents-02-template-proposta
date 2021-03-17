package br.com.zup.propostarefatorada.cartao.biometria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api//biometrias")
public class BiometriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> insereBiometria(@PathVariable String id, @RequestBody @Valid NovaBiometriaRequest request, UriComponentsBuilder uri){

        Biometria biometria = request.toModel(id, manager);

        manager.persist(biometria);

        URI link = uri.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();

        return ResponseEntity.created(link).build();
    }
}
