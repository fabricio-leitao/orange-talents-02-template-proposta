package br.com.zup.propostarefatorada.cartao.integracao.carteira;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, String> {
    boolean existsByEmissor(CarteiraDigital emissor);
}
