package tech.fiap.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class VideoStatusMessage {

	private String videoName;

	private String status;

	private String userEmail;

}
