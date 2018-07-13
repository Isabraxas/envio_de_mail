package com.viridian.kafkajson.producer;

import com.viridian.kafkajson.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by marcelo on 12-07-18
 */
@Slf4j
@Service
public class ClientSender {
    @Autowired
    private KafkaTemplate<String,Client> kafkaTemplate;

    @Value("${viridian.json.topic}")
    private String topic;

    public void send(Client data){
        log.info("Sending data='{}' to topic='{}'",data,topic);

        Message<Client> message = MessageBuilder
                                        .withPayload(data)
                                        .setHeader(KafkaHeaders.TOPIC,topic)
                                        .build();
        kafkaTemplate.send(message);
    }
}
