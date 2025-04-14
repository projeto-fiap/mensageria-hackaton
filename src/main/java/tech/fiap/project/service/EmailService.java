package tech.fiap.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String userEmail, String videoName, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Status do Vídeo: " + videoName);
        message.setText("O status do seu vídeo " + videoName + " é: " + status);

        mailSender.send(message);
        log.info("E-mail enviado para: " + userEmail);
    }
}

