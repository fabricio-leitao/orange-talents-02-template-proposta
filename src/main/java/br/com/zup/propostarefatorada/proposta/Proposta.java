package br.com.zup.propostarefatorada.proposta;

import br.com.zup.propostarefatorada.cartao.Cartao;
import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoRequest;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoResponse;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String numeroCartao;
    @NotBlank
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    private BigDecimal salario;

    @NotNull
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull @Enumerated(EnumType.STRING)
    private StatusProposta status = StatusProposta.PENDENTE;

    private LocalDateTime updatedAt;

    @OneToOne
    private Cartao cartao;


    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getId() {
        return id;
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

    public StatusProposta getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void updateStatus(StatusProposta status) {

        if(status == null){
            throw new IllegalArgumentException("Status can not be null");
        }
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }


    public CartaoAssociadoRequest toModel() {
        return new CartaoAssociadoRequest(documento, nome, id);
    }

    public void updateCartao(Cartao cartao) {
        this.cartao = cartao;
        this.numeroCartao = cartao.getNumeroCartao();
    }
}
