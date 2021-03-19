package br.com.zup.propostarefatorada.proposta;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    Optional<Proposta> findById(String idProposta);

    List<Proposta> findFirst10ByStatusAndCartaoIsNull(StatusProposta status);

    Proposta findByEmail(String email);
}
