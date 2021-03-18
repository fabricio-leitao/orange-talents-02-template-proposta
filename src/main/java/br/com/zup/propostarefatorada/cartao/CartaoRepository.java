package br.com.zup.propostarefatorada.cartao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
