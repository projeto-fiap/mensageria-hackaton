package integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import tech.fiap.project.ProjectApplication;
import tech.fiap.project.dto.PersonWithVideoDTO;
import tech.fiap.project.dto.VideoStatusMessage;
import tech.fiap.project.service.EmailService;
import tech.fiap.project.service.VideoInfoService;
import tech.fiap.project.strategy.OkStatusEmailStrategy;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = ProjectApplication.class)
@ActiveProfiles("test")
@ComponentScan(basePackages = "tech.fiap.project")
class OkStatusEmailStrategyIIntegrationTest {

	@Autowired
	private OkStatusEmailStrategy okStatusEmailStrategy;

	@MockBean
	private VideoInfoService videoInfoService;

	@MockBean
	private EmailService emailService;

	@Test
	void handle_shouldSendEmailWhenVideoProcessedSuccessfully() {

		VideoStatusMessage message = new VideoStatusMessage();
		message.setVideoId("video123");

		PersonWithVideoDTO personDTO = new PersonWithVideoDTO();
		personDTO.setPersonEmail("user@test.com");
		personDTO.setVideoNome("Meu Vídeo");

		when(videoInfoService.fetchPersonByVideoHash("video123")).thenReturn(personDTO);

		okStatusEmailStrategy.handle(message);

		verify(videoInfoService).fetchPersonByVideoHash("video123");
		verify(emailService).sendEmail("user@test.com", "Meu Vídeo", "Seu vídeo foi processado com sucesso!");
	}

	@Test
	void handle_shouldLogErrorWhenPersonNotFound() {

		VideoStatusMessage message = new VideoStatusMessage();
		message.setVideoId("video-not-found");

		when(videoInfoService.fetchPersonByVideoHash("video-not-found")).thenReturn(null);

		okStatusEmailStrategy.handle(message);

		verify(videoInfoService).fetchPersonByVideoHash("video-not-found");
		verify(emailService, never()).sendEmail(any(), any(), any());
	}

}