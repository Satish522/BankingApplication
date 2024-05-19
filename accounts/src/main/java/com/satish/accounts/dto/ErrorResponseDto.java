package com.satish.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API Path invoked bu client", example = "uri=/api/v1/account"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened ", example = "BAD_REQUEST"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened", example = "Customer Already Exist with given mobile Number 9999099990"
    )
    private String errorMessage;

    @Schema(
            description = "time representing when the error happened", example = "200"
    )
    private LocalDateTime errorTime;
}
