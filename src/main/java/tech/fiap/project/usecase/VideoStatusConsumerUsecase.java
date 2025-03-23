package tech.fiap.project.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.fiap.project.domain.VideoStatusMessage;
import tech.fiap.project.infra.kafka.properties.EmailConsumerProperties;

@Service
@RequiredArgsConstructor
public class VideoStatusConsumerUsecase {

	private final JavaMailSender mailSender;
	private final EmailConsumerProperties properties;

	private final ObjectMapper objectMapper = new ObjectMapper(); // Instancia o ObjectMapper para conversão

	@Value("${spring.kafka.email-consumer.topic}")
	private String emailTopic;

	@KafkaListener(topics = "#{@emailConsumerProperties.topic}", groupId = "#{@emailConsumerProperties.groupId}")
	public void consumeVideoStatus(String message) {
		try {
			VideoStatusMessage videoStatus = objectMapper.readValue(message, VideoStatusMessage.class);

			System.out.println("Received video status: " + videoStatus);

			sendEmail(videoStatus.getUserEmail(), videoStatus.getVideoName(), videoStatus.getStatus());
		} catch (Exception e) {
			System.err.println("Erro ao processar a mensagem: " + e.getMessage());
		}
	}

	private void sendEmail(String userEmail, String videoName, String status) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(userEmail);
		message.setSubject("Status do Vídeo: " + videoName);
		message.setText("O status do seu vídeo " + videoName + " é: " + status);

		mailSender.send(message);
		System.out.println("E-mail enviado para: " + userEmail);
	}
}
