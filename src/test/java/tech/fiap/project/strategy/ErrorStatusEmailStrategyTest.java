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
class ErrorStatusEmailStrategyTest {

	@Mock
	private VideoInfoService videoInfoService;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private ErrorStatusEmailStrategy errorStatusEmailStrategy;

	private final String VIDEO_HASH = "video123";

	private VideoStatusMessage errorMessage;

	private PersonWithVideoDTO personWithVideoDTO;

	@BeforeEach
	void setUp() {
		errorMessage = new VideoStatusMessage();
		errorMessage.setVideoId(VIDEO_HASH);
		errorMessage.setStatus(VideoStatus.ERRO);

		personWithVideoDTO = new PersonWithVideoDTO(1L, "John Doe", "12345678901", "user@example.com", 1L, "My Video",
				"http://example.com/video123", "ERROR", LocalDateTime.now(), LocalDateTime.now());
	}

	@Test
	void handle_shouldSendErrorEmail_whenPersonExists() {
		when(videoInfoService.fetchPersonByVideoHash(VIDEO_HASH)).thenReturn(personWithVideoDTO);

		errorStatusEmailStrategy.handle(errorMessage);

		verify(videoInfoService).fetchPersonByVideoHash(VIDEO_HASH);
		verify(emailService).sendEmail("user@example.com", "My Video",
				"Erro no processamento do video! Consulte a plataforma para mais informações!");
	}

	@Test
	void handle_shouldNotSendEmail_whenPersonNotFound() {
		when(videoInfoService.fetchPersonByVideoHash(VIDEO_HASH)).thenReturn(null);

		errorStatusEmailStrategy.handle(errorMessage);

		verify(videoInfoService).fetchPersonByVideoHash(VIDEO_HASH);
		verify(emailService, never()).sendEmail(any(), any(), any());
	}

	@Test
	void handle_shouldSendCorrectErrorMessage() {
		PersonWithVideoDTO testData = new PersonWithVideoDTO(2L, "Jane Smith", "98765432109", "jane@example.com", 2L,
				"Failed Video", "http://example.com/video456", "ERROR", LocalDateTime.now(), LocalDateTime.now());
		when(videoInfoService.fetchPersonByVideoHash(VIDEO_HASH)).thenReturn(testData);

		errorStatusEmailStrategy.handle(errorMessage);

		verify(emailService).sendEmail("jane@example.com", "Failed Video",
				"Erro no processamento do video! Consulte a plataforma para mais informações!");
	}

}