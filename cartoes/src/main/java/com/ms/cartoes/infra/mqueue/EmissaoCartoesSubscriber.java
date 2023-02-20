package com.ms.cartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartoesSubscriber {

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSlocitacaoEmissao(@Payload String payload) {
        System.out.println(payload);
    }

}
