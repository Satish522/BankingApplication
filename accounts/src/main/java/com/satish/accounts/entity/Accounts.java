package com.satish.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Accounts extends BaseEntity {

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "customer_id")
    private Long customerId;

    private String accountType;

    private String branchAddress;

    @Column(name = "communication_sw")
    private Boolean communicationSw;

}
