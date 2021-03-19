package br.com.isaquebrb.votesession.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${cloudkarafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void processMessage(String message) {
        log.info("Method processMessage - Mensagem recebida=[{}].", message);
    }
}
