package com.satish.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Schema to hold response information"
)
@Data
@AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code of response", example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message of response", example = "Request processed successfully"
    )
    private String statusMessage;
}
