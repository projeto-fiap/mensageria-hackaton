package tech.fiap.project.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.dto.VideoStatus;
import tech.fiap.project.dto.VideoStatusMessage;
import tech.fiap.project.strategy.EmailStrategy;
import tech.fiap.project.strategy.EmailStrategyFactory;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoStatusConsumerTest {

	@Mock
	private EmailStrategyFactory strategyFactory;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private VideoStatusConsumer videoStatusConsumer;

	@Test
	void consumeVideoStatus_shouldProcessValidMessage() throws Exception {
		String jsonMessage = "{\"videoId\":\"123\",\"status\":\"FINALIZADO\"}";
		VideoStatusMessage statusMessage = new VideoStatusMessage();
		statusMessage.setVideoId("123");
		statusMessage.setStatus(VideoStatus.FINALIZADO);

		EmailStrategy mockStrategy = mock(EmailStrategy.class);

		when(objectMapper.readValue(jsonMessage, VideoStatusMessage.class)).thenReturn(statusMessage);
		when(strategyFactory.getStrategy("FINALIZADO")).thenReturn(java.util.Optional.of(mockStrategy));

		videoStatusConsumer.consumeVideoStatus(jsonMessage);

		verify(objectMapper).readValue(jsonMessage, VideoStatusMessage.class);
		verify(strategyFactory).getStrategy("FINALIZADO");
		verify(mockStrategy).handle(statusMessage);
	}

	@Test
	void consumeVideoStatus_shouldLogErrorWhenJsonInvalid() throws Exception {
		String invalidJson = "invalid-json";

		when(objectMapper.readValue(invalidJson, VideoStatusMessage.class))
				.thenThrow(new JsonProcessingException("Invalid JSON") {
				});

		videoStatusConsumer.consumeVideoStatus(invalidJson);

		verify(objectMapper).readValue(invalidJson, VideoStatusMessage.class);
		verifyNoInteractions(strategyFactory);
	}

}