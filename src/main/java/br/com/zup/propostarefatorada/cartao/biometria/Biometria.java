package br.com.zup.propostarefatorada.cartao.biometria;

import br.com.zup.propostarefatorada.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String codigo;

    @PastOrPresent
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cartao cartao;

    @Deprecated
    public Biometria(){}

    public Biometria(@NotBlank String codigo, Cartao cartao) {
        this.codigo = codigo;
        this.dataCriacao = LocalDateTime.now();
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }


}
