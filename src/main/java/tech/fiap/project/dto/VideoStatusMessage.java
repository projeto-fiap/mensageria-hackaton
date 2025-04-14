package tech.fiap.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoStatusMessage {

	private String videoId;

	private String storage;

	private String status;

}
