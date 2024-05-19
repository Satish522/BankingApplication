package com.satish.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 50, message = "Length of charecter should be 5 to 50")
    private String name;

    @NotEmpty(message = "Email address required")
    @Email(message = "Email address not valid")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
