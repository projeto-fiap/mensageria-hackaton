package tech.fiap.project.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.fiap.project.dto.PersonWithVideoDTO;
import tech.fiap.project.dto.VideoStatusMessage;
import tech.fiap.project.service.EmailService;
import tech.fiap.project.service.VideoInfoService;

@Component
@Slf4j
public class OkStatusEmailStrategy implements EmailStrategy {

	private final VideoInfoService videoInfoService;

	private final EmailService emailService;

	public OkStatusEmailStrategy(VideoInfoService videoInfoService, EmailService emailService) {
		this.videoInfoService = videoInfoService;
		this.emailService = emailService;
	}

	@Override
	public void handle(VideoStatusMessage message) {
		PersonWithVideoDTO personWithVideoDTO = videoInfoService.fetchPersonByVideoHash(message.getVideoId());
		if (personWithVideoDTO == null) {
			log.error("Pessoa não encontrada para o vídeo com hash: " + message.getVideoId());
			return;
		}
		emailService.sendEmail(personWithVideoDTO.getPersonEmail(), personWithVideoDTO.getVideoNome(),
				"Seu vídeo foi processado com sucesso!");
	}

}
