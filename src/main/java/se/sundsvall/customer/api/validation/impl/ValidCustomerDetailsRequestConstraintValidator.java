package se.sundsvall.customer.api.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.api.validation.ValidCustomerDetailsRequest;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

public class ValidCustomerDetailsRequestConstraintValidator implements ConstraintValidator<ValidCustomerDetailsRequest, CustomerDetailsRequest> {

	@Override
	public boolean isValid(final CustomerDetailsRequest request, final ConstraintValidatorContext context) {
		return !(isBlank(request.getCustomerEngagementOrgId()));
	}
}
