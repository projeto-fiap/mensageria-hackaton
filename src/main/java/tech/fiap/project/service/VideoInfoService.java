package tech.fiap.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.dto.PersonWithVideoDTO;

@Service
public class VideoInfoService {

    private final RestTemplate restTemplate;
    private final String videoServiceBaseUrl;

    public VideoInfoService(
            RestTemplate restTemplate,
            @Value("${video.service.url}") String videoServiceBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.videoServiceBaseUrl = videoServiceBaseUrl;
    }

    public PersonWithVideoDTO fetchPersonByVideoHash(String hashNome) {
        String url = videoServiceBaseUrl + "/video/hash/" + hashNome;
        ResponseEntity<PersonWithVideoDTO> response = restTemplate.getForEntity(url, PersonWithVideoDTO.class);
        return response.getBody();
    }
}
