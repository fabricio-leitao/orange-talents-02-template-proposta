package br.com.zup.propostarefatorada.cartao;

import br.com.zup.propostarefatorada.cartao.integracao.AssociaPropostaCartaoClient;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoRequest;
import br.com.zup.propostarefatorada.cartao.integracao.CartaoAssociadoResponse;
import br.com.zup.propostarefatorada.proposta.Proposta;
import br.com.zup.propostarefatorada.proposta.PropostaRepository;
import br.com.zup.propostarefatorada.proposta.StatusProposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CartaoScheduler {

    @Autowired
    private AssociaPropostaCartaoClient associaPropostaCartaoClient;
    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    @Scheduled(fixedDelayString = "${scheduler.pesquisa-cartao}")
    @Transactional
    public void associaCartao() {
        List<Proposta> listaPropostaSemCartao = propostaRepository.findFirst10ByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);

        listaPropostaSemCartao.forEach(propostas -> {
            Proposta proposta = listaPropostaSemCartao.get(0);
            CartaoAssociadoResponse cartaoAssociadoResponse = associaPropostaCartaoClient.associarPropostaCartao(new CartaoAssociadoRequest(proposta));
            Cartao cartao = cartaoAssociadoResponse.toModel(proposta);
            cartaoRepository.save(cartao);
            proposta.updateCartao(cartao);
            propostaRepository.save(proposta);
        });
    }
}