package tech.fiap.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithVideoDTO {

	private Long personId;

	private String personNome;

	private String personCpf;

	private String personEmail;

	private Long videoId;

	private String videoNome;

	private String videoUrl;

	private String videoStatus;

	private LocalDateTime videoDataCriacao;

	private LocalDateTime videoDataAtualizacao;

}