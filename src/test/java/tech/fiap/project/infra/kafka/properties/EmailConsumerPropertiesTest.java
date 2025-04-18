package tech.fiap.project.infra.kafka.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailConsumerPropertiesTest {

	private EmailConsumerProperties emailConsumerProperties;

	@BeforeEach
	void setUp() {
		emailConsumerProperties = new EmailConsumerProperties();
	}

	@Test
	void testGetAndSetTopic() {
		String topic = "test-topic";
		emailConsumerProperties.setTopic(topic);
		assertEquals(topic, emailConsumerProperties.getTopic());
	}

	@Test
	void testGetAndSetGroupId() {
		String groupId = "test-group";
		emailConsumerProperties.setGroupId(groupId);
		assertEquals(groupId, emailConsumerProperties.getGroupId());
	}

}