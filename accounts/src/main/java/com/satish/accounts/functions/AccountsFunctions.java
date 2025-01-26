package com.satish.accounts.functions;

import com.satish.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * Class AccountsFunctions
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 26/01/25
 */
@Configuration
public class AccountsFunctions {
    private static final Logger log = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountService iAccountService){
        return accountNumber -> {
            log.info("Updating communication status for the account number: {}", accountNumber.toString());
            iAccountService.updateCommunicatonStatus(accountNumber);
        };
    }
}
