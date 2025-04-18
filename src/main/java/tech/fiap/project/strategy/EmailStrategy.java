package tech.fiap.project.strategy;

import tech.fiap.project.dto.VideoStatusMessage;

public interface EmailStrategy {

	void handle(VideoStatusMessage message);

}
