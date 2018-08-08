package com.viridian.kafkajson.consumer;

import com.viridian.kafkajson.mail.EmailService;
import com.viridian.kafkajson.mail.Mail;
import com.viridian.kafkajson.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
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

    @Autowired
    private EmailService emailService;

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

            //Send email
            Mail mail = new Mail();
            mail.setFrom("no-reply@memorynotfound.com");
            mail.setTo("isvar.vega@gmail.com");
            mail.setSubject("Sending Email with Attachment Example");
            mail.setContent("Content added with Spring Framework by send attached image test");
            emailService.sendImageKafkaMessage(mail,imageInByte,data.getExtensionFile());
            //End send email

            ImageIO.write(bImageFromConvert, data.getExtensionFile(), new File("/home/isvar/Desktop/testimg-received.png"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
