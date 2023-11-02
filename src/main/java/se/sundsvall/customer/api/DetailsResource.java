package se.sundsvall.customer.api;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.violations.ConstraintViolationProblem;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.service.CustomerService;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@Validated
@RequestMapping("/details")
@Tag(name = "Details", description = "Details operations")
public class DetailsResource {

    private final CustomerService customerService;

    public DetailsResource(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
    @Operation(summary = "Get customer by party-ID or organization id for customer engagements")
    @ApiResponse(responseCode = "200", description = "Successful Operation", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    @ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    public ResponseEntity<CustomerDetailsResponse> getCustomerDetails(@Valid @RequestBody final CustomerDetailsRequest request) {
        // Make sure that partyId or customerEngagementOrgId is provided and non-empty
        if (isEmpty(request.getPartyId()) && isBlank(request.getCustomerEngagementOrgId())) {
            throw Problem.valueOf(Status.BAD_REQUEST, "'partyId' or 'customerEngagementOrgId' must be provided");
        }

        return ok(customerService.getCustomerDetails(request));
    }
}
