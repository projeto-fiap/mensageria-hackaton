package tech.fiap.project.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tech.fiap.project.domain.VideoStatusMessage;

@Service
@RequiredArgsConstructor
public class VideoStatusProducer {

	private final KafkaTemplate<String, VideoStatusMessage> kafkaTemplate;

	private final String topic = "video-status-topic"; // Pode ser configurado no
														// application.properties

	public void sendVideoStatus(VideoStatusMessage message) {
		kafkaTemplate.send(topic, message);
		System.out.println("Mensagem enviada para o Kafka: " + message);
	}

}
