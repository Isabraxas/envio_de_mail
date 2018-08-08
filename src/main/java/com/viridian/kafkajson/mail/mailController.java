package com.viridian.kafkajson.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class mailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/send")
    public String sendMessage() throws Exception {
        log.info("Spring Mail - Sending Email with Attachment test");

        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("isvar.vega@gmail.com");
        mail.setSubject("Sending Email with Attachment Example");
        mail.setContent("Content added with Spring Framework by send attached image test");

        emailService.sendSimpleMessage(mail);

        return "Mensaje enviado";
    }

}
