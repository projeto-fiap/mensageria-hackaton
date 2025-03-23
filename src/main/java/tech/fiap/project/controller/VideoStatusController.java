package tech.fiap.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.fiap.project.domain.VideoStatusMessage;
import tech.fiap.project.usecase.VideoStatusProducer;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoStatusController {

	private final VideoStatusProducer producer;

	@PostMapping("/status")
	public String sendVideoStatus(@RequestBody VideoStatusMessage message) {
		producer.sendVideoStatus(message);
		return "Mensagem enviada!";
	}

}
