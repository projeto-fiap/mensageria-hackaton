package tech.fiap.project.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tech.fiap.project.dto.VideoStatusMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoStatusProducer {

	private final KafkaTemplate<String, VideoStatusMessage> kafkaTemplate;

	private final String topic = "v1.video-upload-status";

	public void sendVideoStatus(VideoStatusMessage message) {
	try {
		kafkaTemplate.send(topic, message);
		log.info("Mensagem enviada para o Kafka: " + message);
	}
	catch (Exception e) {
		log.error("Erro ao enviar mensagem para o Kafka: " + e.getMessage());
	}
}
}
