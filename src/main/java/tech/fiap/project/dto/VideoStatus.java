package tech.fiap.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum VideoStatus {
	@JsonProperty("ERRO")
	ERRO,
	@JsonProperty("FINALIZADO")
	FINALIZADO,
	@JsonProperty("RECEBIDO")
	RECEBIDO,
	@JsonProperty("PROCESSANDO")
	PROCESSANDO
}