package br.com.zup.propostarefatorada.cartao.integracao.carteira;

import br.com.zup.propostarefatorada.cartao.Cartao;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraDigital carteira;

    public String getEmail() {
        return email;
    }

    public CarteiraDigital getCarteira() {
        return carteira;
    }

    public Carteira toModel(Cartao cartao, String id) {
        return new Carteira(id, email, carteira, cartao);
    }
}
