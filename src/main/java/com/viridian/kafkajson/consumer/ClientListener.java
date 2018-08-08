package com.viridian.kafkajson.consumer;

import com.viridian.kafkajson.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelo on 12-07-18
 */
@Slf4j
@Service
public class ClientListener {
    @KafkaListener(topics = "${viridian.json.topic}")
    public void receive(@Payload Client data,
                        @Headers MessageHeaders headers) {
        log.info("received data='{}'", data);

        headers.keySet().forEach(key -> {
            log.info("{}: {}", key, headers.get(key));

        });
        // procesar imagen
        try{
            byte[] imageInByte;
            imageInByte = data.getFoto();


            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, "png", new File(
                    "/home/isvar/Desktop/testimg-received.png"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
