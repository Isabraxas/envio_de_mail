package com.viridian.kafkajson.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    @Value("${file.name.classpath}")
    private String fileName;

    @Value("${file.extension.classpath}")
    private String fileExtension;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(Mail mail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());
        helper.setTo(mail.getTo());
        helper.setFrom(mail.getTo());

        helper.addAttachment("attachment-document-image"+fileExtension, new ClassPathResource(fileName+fileExtension));

        emailSender.send(message);

    }

    public void sendImageKafkaMessage(Mail mail, byte[] bytesfile) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());
        helper.setTo(mail.getTo());
        helper.setFrom(mail.getTo());

        //helper.addAttachment("attachment-document-image"+fileExtension, new InputStreamResource(inputStream));
        //helper.addAttachment("attachment-document-image"+fileExtension,new ByteArrayResource(bytesfile), MediaType.APPLICATION_PDF.toString());
        helper.addAttachment("attachment-document-image"+fileExtension,new ByteArrayResource(bytesfile), MediaType.IMAGE_PNG.toString());
        emailSender.send(message);

    }

    public void sendPdfKafkaMessage(Mail mail, byte[] bytesfile) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());
        helper.setTo(mail.getTo());
        helper.setFrom(mail.getTo());

        //helper.addAttachment("attachment-document-image"+fileExtension, new InputStreamResource(inputStream));
        helper.addAttachment("attachment-document-image"+.pdf,new ByteArrayResource(bytesfile), MediaType.APPLICATION_PDF.toString());
        emailSender.send(message);

    }

}
