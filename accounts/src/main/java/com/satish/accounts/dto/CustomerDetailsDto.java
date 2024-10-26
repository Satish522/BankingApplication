package com.satish.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Class CustomerDetailsDto
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 26/10/24
 */

@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer , Cards, Loan information"
)
@Data
public class CustomerDetailsDto {


    @Schema(
            description = "Name of Customer",example = "Satish Kumar Subudhi"
    )
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 50, message = "Length of charecter should be 5 to 50")
    private String name;

    @Schema(
            description = "Email of Customer",example = "satishsubudhi522@gmail.com"
    )
    @NotEmpty(message = "Email address required")
    @Email(message = "Email address not valid")
    private String email;

    @Schema(
            description = "Mobile number of Customer",example = "7873482121"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;

    @Schema(
            description = "Account details of Customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Cards details of Customer"
    )
    private CardsDto cardsDto;

    @Schema(
            description = "Loans details of Customer"
    )
    private LoansDto loansDto;
}
