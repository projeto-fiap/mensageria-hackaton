package tech.fiap.project.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.fiap.project.dto.VideoStatusMessage;
import tech.fiap.project.strategy.EmailStrategy;
import tech.fiap.project.strategy.EmailStrategyFactory;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoStatusConsumer {

	private final EmailStrategyFactory strategyFactory;

	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "${spring.kafka.email-consumer.topic}", groupId = "notifier-service")
	public void consumeVideoStatus(String message) {
		try {
			VideoStatusMessage videoStatus = objectMapper.readValue(message, VideoStatusMessage.class);
			log.info("Received video status: " + videoStatus);

			EmailStrategy strategy = strategyFactory.getStrategy(String.valueOf(videoStatus.getStatus())).get();
			strategy.handle(videoStatus);
		}
		catch (Exception e) {
			log.error("Erro ao processar a mensagem: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
