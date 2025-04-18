package tech.fiap.project.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.dto.PersonWithVideoDTO;
import tech.fiap.project.dto.VideoStatus;
import tech.fiap.project.dto.VideoStatusMessage;
import tech.fiap.project.service.EmailService;
import tech.fiap.project.service.VideoInfoService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OkStatusEmailStrategyTest {

	@Mock
	private VideoInfoService videoInfoService;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private OkStatusEmailStrategy okStatusEmailStrategy;

	private final String VIDEO_HASH = "video123";

	private VideoStatusMessage validMessage;

	private PersonWithVideoDTO personWithVideoDTO;

	@BeforeEach
	void setUp() {
		validMessage = new VideoStatusMessage();
		validMessage.setVideoId(VIDEO_HASH);
		validMessage.setStatus(VideoStatus.FINALIZADO);

		personWithVideoDTO = new PersonWithVideoDTO(1L, "John Doe", "12345678901", "user@example.com", 1L, "My Video",
				"http://example.com/video123", "PROCESSED", LocalDateTime.now(), LocalDateTime.now());
	}

	@Test
	void handle_shouldFetchPersonAndSendEmail_whenPersonExists() {
		when(videoInfoService.fetchPersonByVideoHash(VIDEO_HASH)).thenReturn(personWithVideoDTO);

		okStatusEmailStrategy.handle(validMessage);

		verify(videoInfoService).fetchPersonByVideoHash(VIDEO_HASH);
		verify(emailService).sendEmail("user@example.com", "My Video", "Seu vídeo foi processado com sucesso!");
	}

	@Test
	void handle_shouldNotSendEmail_whenPersonNotFound() {
		when(videoInfoService.fetchPersonByVideoHash(VIDEO_HASH)).thenReturn(null);

		okStatusEmailStrategy.handle(validMessage);

		verify(videoInfoService).fetchPersonByVideoHash(VIDEO_HASH);
		verify(emailService, never()).sendEmail(any(), any(), any());
	}

	@Test
	void handle_shouldUseCorrectEmailAndVideoName() {
		PersonWithVideoDTO testData = new PersonWithVideoDTO(2L, "Jane Smith", "98765432109", "jane@example.com", 2L,
				"Another Video", "http://example.com/video456", "PROCESSED", LocalDateTime.now(), LocalDateTime.now());
		when(videoInfoService.fetchPersonByVideoHash(VIDEO_HASH)).thenReturn(testData);

		okStatusEmailStrategy.handle(validMessage);

		verify(emailService).sendEmail("jane@example.com", "Another Video", "Seu vídeo foi processado com sucesso!");
	}

}