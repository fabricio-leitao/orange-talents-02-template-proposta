package br.com.zup.propostarefatorada.proposta;

import br.com.zup.propostarefatorada.validacao.CPFOuCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotNull
    @CPFOuCNPJ
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    private BigDecimal salario;

    @Deprecated
    public NovaPropostaRequest(){}

    public NovaPropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta toModel() {
        return new Proposta(documento, email, nome, endereco, salario);
    }
}
