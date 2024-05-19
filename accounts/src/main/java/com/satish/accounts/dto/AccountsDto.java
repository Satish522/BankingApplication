package com.satish.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Accounts can have account number , account type , Branch number"
)
@Data
public class AccountsDto {

    @Schema(
            description = "Account number of Customer",example = "7873482121"
    )
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digit")
    private Long accountNumber;

    @Schema(
            description = "Account type of Customer",example = "7873482121"
    )
    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;

    @Schema(
            description = "Branch address of Customer",example = "7873482121"
    )
    @NotEmpty(message = "Branch address can not be null or empty")
    private String branchAddress;
}
