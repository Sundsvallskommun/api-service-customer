package se.sundsvall.customer.api.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.customer.api.model.CustomerType.ENTERPRISE;
import static se.sundsvall.customer.api.model.CustomerType.PRIVATE;

class CustomerTypeTest {

	@Test
	void enums() {
		assertThat(CustomerType.values()).containsExactly(PRIVATE, ENTERPRISE);
	}

	@Test
	void enumValues() {
		assertThat(PRIVATE).hasToString("PRIVATE");
		assertThat(ENTERPRISE).hasToString("ENTERPRISE");
	}
}
