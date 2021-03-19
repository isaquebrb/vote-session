package br.com.isaquebrb.votesession.kafka;

import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${cloudkarafka.topic}")
    private String topic;

    public void send(TopicResponse message) {
        String key = String.valueOf(System.currentTimeMillis());

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, key, message.toString());

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Method send onSucess - Mensagem enviada=[{}] com offset=[{}]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Nao foi possivel enviar a mensagem=[{}]. Erro: {}", message, ex.getMessage(), ex);
            }
        });
    }
}
