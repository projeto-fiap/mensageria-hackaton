package tech.fiap.project.infra;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import tech.fiap.project.dto.VideoStatusMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaVideoStatusProducerConfig {

	// Configuração do ProducerFactory
	@Bean
	public ProducerFactory<String, VideoStatusMessage> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new JsonSerializer<>());
	}

	// Configuração do KafkaTemplate usando o ProducerFactory
	@Bean
	public KafkaTemplate<String, VideoStatusMessage> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	// Configuração das propriedades do Kafka
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
		props.put(ProducerConfig.RETRIES_CONFIG, 3);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		return props;
	}
}
