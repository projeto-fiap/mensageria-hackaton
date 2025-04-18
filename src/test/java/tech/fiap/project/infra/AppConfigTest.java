package tech.fiap.project.infra;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

	@org.junit.jupiter.api.Test
	void restTemplate() {
		AppConfig appConfig = new AppConfig();

		org.springframework.web.client.RestTemplate result = appConfig.restTemplate();

		assertNotNull(result);
	}

}