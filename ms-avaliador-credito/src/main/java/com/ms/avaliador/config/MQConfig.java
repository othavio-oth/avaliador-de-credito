package com.ms.avaliador.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;

public class MQConfig {

    @Value("${mq.queues.emissao-cartoes}")
    private String filaEmissaoCartoes;

    public Queue queueEmissaoCartoes() {
        return new Queue(filaEmissaoCartoes, true);
    }

}
