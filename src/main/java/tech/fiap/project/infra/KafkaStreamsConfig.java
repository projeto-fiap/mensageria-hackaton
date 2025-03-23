package tech.fiap.project.infra;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

	@Bean
	public KStream<String, String> kafkaStream(StreamsBuilder streamsBuilder) {
		KStream<String, String> videoStream = streamsBuilder.stream("video-topic");

		// Processar as mensagens
		videoStream.mapValues(value -> {
			return "Processed: " + value;
		}).to("processed-video-topic", Produced.with(Serdes.String(), Serdes.String()));

		return videoStream;
	}

}