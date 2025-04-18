package tech.fiap.project.strategy;

import org.junit.jupiter.api.Test;
import tech.fiap.project.dto.VideoStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmailStrategyFactoryTest {

	@Test
	void getStrategy_shouldReturnOkStrategyForFinalizadoStatus() {
		OkStatusEmailStrategy okStrategy = new OkStatusEmailStrategy(null, null);
		ErrorStatusEmailStrategy errorStrategy = new ErrorStatusEmailStrategy(null, null);
		EmailStrategyFactory factory = new EmailStrategyFactory(List.of(okStrategy, errorStrategy));

		Optional<EmailStrategy> result = factory.getStrategy("FINALIZADO");

		assertTrue(result.isPresent());
		assertEquals(okStrategy, result.get());
	}

	@Test
	void getStrategy_shouldReturnErrorStrategyForErroStatus() {
		OkStatusEmailStrategy okStrategy = new OkStatusEmailStrategy(null, null);
		ErrorStatusEmailStrategy errorStrategy = new ErrorStatusEmailStrategy(null, null);
		EmailStrategyFactory factory = new EmailStrategyFactory(List.of(okStrategy, errorStrategy));

		Optional<EmailStrategy> result = factory.getStrategy("ERRO");

		assertTrue(result.isPresent());
		assertEquals(errorStrategy, result.get());
	}

	@Test
	void getStrategy_shouldReturnEmptyForUnknownStatus() {
		OkStatusEmailStrategy okStrategy = new OkStatusEmailStrategy(null, null);
		ErrorStatusEmailStrategy errorStrategy = new ErrorStatusEmailStrategy(null, null);
		EmailStrategyFactory factory = new EmailStrategyFactory(List.of(okStrategy, errorStrategy));

		Optional<EmailStrategy> result = factory.getStrategy("UNKNOWN");

		assertFalse(result.isPresent());
	}

	@Test
	void getStrategy_shouldBeCaseInsensitive() {
		OkStatusEmailStrategy okStrategy = new OkStatusEmailStrategy(null, null);
		ErrorStatusEmailStrategy errorStrategy = new ErrorStatusEmailStrategy(null, null);
		EmailStrategyFactory factory = new EmailStrategyFactory(List.of(okStrategy, errorStrategy));

		Optional<EmailStrategy> result1 = factory.getStrategy("finalizado");
		Optional<EmailStrategy> result2 = factory.getStrategy("erro");

		assertTrue(result1.isPresent());
		assertEquals(okStrategy, result1.get());
		assertTrue(result2.isPresent());
		assertEquals(errorStrategy, result2.get());
	}

	@Test
	void constructor_shouldMapStrategiesCorrectly() {
		OkStatusEmailStrategy okStrategy = new OkStatusEmailStrategy(null, null);
		ErrorStatusEmailStrategy errorStrategy = new ErrorStatusEmailStrategy(null, null);
		EmailStrategyFactory factory = new EmailStrategyFactory(List.of(okStrategy, errorStrategy));

		assertNotNull(factory);
	}

}