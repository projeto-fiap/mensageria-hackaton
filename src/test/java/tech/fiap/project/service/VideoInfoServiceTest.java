package tech.fiap.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.dto.PersonWithVideoDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoInfoServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private VideoInfoService videoInfoService;

	private final String BASE_URL = "http://video-service";

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(videoInfoService, "videoServiceBaseUrl", BASE_URL);
	}

	@Test
	void fetchPersonByVideoHash_shouldReturnPersonWithVideoDTO() {
		String hash = "video123";
		String expectedUrl = BASE_URL + "/video/hash/" + hash;
		PersonWithVideoDTO expectedResponse = new PersonWithVideoDTO();

		when(restTemplate.getForEntity(expectedUrl, PersonWithVideoDTO.class))
				.thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

		PersonWithVideoDTO result = videoInfoService.fetchPersonByVideoHash(hash);

		assertNotNull(result);
		assertEquals(expectedResponse, result);
		verify(restTemplate).getForEntity(expectedUrl, PersonWithVideoDTO.class);
	}

	@Test
	void fetchPersonByVideoHash_shouldReturnNullWhenResponseIsNull() {
		String hash = "video456";
		String expectedUrl = BASE_URL + "/video/hash/" + hash;

		when(restTemplate.getForEntity(expectedUrl, PersonWithVideoDTO.class))
				.thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

		PersonWithVideoDTO result = videoInfoService.fetchPersonByVideoHash(hash);

		assertNull(result);
		verify(restTemplate).getForEntity(expectedUrl, PersonWithVideoDTO.class);
	}

	@Test
	void fetchPersonByVideoHash_shouldBuildCorrectUrl() {
		String hash = "test-hash";
		String expectedUrl = BASE_URL + "/video/hash/" + hash;
		PersonWithVideoDTO mockResponse = new PersonWithVideoDTO();

		when(restTemplate.getForEntity(expectedUrl, PersonWithVideoDTO.class))
				.thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

		PersonWithVideoDTO result = videoInfoService.fetchPersonByVideoHash(hash);

		assertNotNull(result);
		verify(restTemplate).getForEntity(expectedUrl, PersonWithVideoDTO.class);
	}

}