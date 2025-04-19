package tech.fiap.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }

    @Test
    void shouldSendEmailWithCorrectContent() {
        String userEmail = "teste@exemplo.com";
        String videoName = "Aula Java";
        String status = "Processado";

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendEmail(userEmail, videoName, status);

        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
        assertArrayEquals(new String[]{userEmail}, sentMessage.getTo());
        assertEquals("Status do Vídeo: " + videoName, sentMessage.getSubject());
        assertEquals("O status do seu vídeo " + videoName + " é: " + status, sentMessage.getText());
    }
}
