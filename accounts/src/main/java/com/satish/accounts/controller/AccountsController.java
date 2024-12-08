package com.satish.accounts.controller;

import com.satish.accounts.constants.AccountConstants;
import com.satish.accounts.dto.*;
import com.satish.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD REST API for Accounts Mocroservice",
        description = "REST API help to create account and manage or update account"
)
public class AccountsController {

    public static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    private IAccountService iAccountService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private ContactDetailRecord contactDetails;

    @Operation(
            summary = "Create Accounts REST API",
            description = "REST API to create new Customer and Accounts in Banking Application"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @PostMapping("/account")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Accounts REST API",
            description = "REST API to fetch Customer and Accounts details using mobile no in Banking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/account")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                               @RequestParam String mobileNumber) {
        CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(
            summary = "Update Accounts REST API",
            description = "REST API to update existing Customer and Accounts in Banking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/account")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete Accounts REST API",
            description = "REST API to delete existing Customer and Accounts using mobileNumber in Banking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/account")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                                @RequestParam String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Get Build Version",
            description = "Give build version through this API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @Retry(name = "getBuildVersion", fallbackMethod = "getBuildVersionFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        logger.debug("getBuildVersion method invoked");
        throw new RuntimeException();
        /*return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);*/
    }

    public ResponseEntity<String> getBuildVersionFallback(Throwable throwable) {
        logger.debug("getBuildVersionFallback method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }
    @Operation(
            summary = "Get Environment",
            description = "Give different type of environment details through this API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @RateLimiter(name = "getEnvironment", fallbackMethod = "getEnvironmentFallback")
    @GetMapping("/environment")
    public ResponseEntity<String> getEnvironment() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getEnvironmentFallback(Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 21");
    }

    @Operation(
            summary = "Get Contact Details",
            description = "Give contact details with description through this API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<ContactDetailRecord> getContactDetails() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactDetails);
    }
}
