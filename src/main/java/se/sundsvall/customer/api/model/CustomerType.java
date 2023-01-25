package se.sundsvall.customer.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer type model")
public enum CustomerType {
	PRIVATE,
	ENTERPRISE;
}
