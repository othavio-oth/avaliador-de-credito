package com.ms.cartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.cartoes.domain.Cartao;
import com.ms.cartoes.domain.ClienteCartao;
import com.ms.cartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.ms.cartoes.infra.repository.CartaoRepository;
import com.ms.cartoes.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmissaoCartoesSubscriber {

    private final ClienteCartaoRepository clienteCartaoRepository;
    private final CartaoRepository cartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSlocitacaoEmissao(@Payload String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);

            Cartao cartao = cartaoRepository.getById(dados.getIdCartao());
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
