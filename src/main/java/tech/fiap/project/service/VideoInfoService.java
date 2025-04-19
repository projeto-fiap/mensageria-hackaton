package tech.fiap.project.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.dto.PersonWithVideoDTO;

import java.util.Map;

@Service
public class VideoInfoService {

	private final RestTemplate restTemplate;

	private final String videoServiceBaseUrl;

	@Value("${video.service.keycloak-client-id}")
	private String clientId;

	@Value("${video.service.keycloak-realm}")
	private String realm;

	@Value("${video.service.keycloak-url}")
	private String baseUrl;
	@Value("${video.service.keycloak-client-secret}")
	private String clientSecret;

	public VideoInfoService(RestTemplate restTemplate, @Value("${video.service.url}") String videoServiceBaseUrl) {
		this.restTemplate = restTemplate;
		this.videoServiceBaseUrl = videoServiceBaseUrl;
	}

	public PersonWithVideoDTO fetchPersonByVideoHash(String hashNome) {
		String url = videoServiceBaseUrl + "/video/hash/" + hashNome;

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getUserToken());

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<PersonWithVideoDTO> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				requestEntity,
				PersonWithVideoDTO.class
		);

		return response.getBody();
	}
		private String getUserToken() {
		String url = String.format("%s/realms/%s/protocol/openid-connect/token", baseUrl, realm);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		ResponseEntity<Map> response = new RestTemplate().postForEntity(url, request, Map.class);

		if (response.getBody() != null){
			return (String) response.getBody().get("access_token");

		}
		return  null;
	}

}
