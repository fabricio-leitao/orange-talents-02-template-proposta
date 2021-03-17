package br.com.zup.propostarefatorada.cartao.biometria;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.validacao.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class NovaBiometriaRequest {

    @NotBlank
    @Base64
    private String codigo;

    public Biometria toModel(String id, EntityManager manager) {

        Cartao cartao = Optional.ofNullable(manager.find(Cartao.class, id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        return new Biometria(codigo, cartao);
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
