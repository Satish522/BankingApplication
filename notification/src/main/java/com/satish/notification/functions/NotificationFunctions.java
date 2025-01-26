package com.satish.notification.functions;

import com.satish.notification.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Class NotificationFunctions
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 25/01/25
 */
@Component
public class NotificationFunctions {

    public static final Logger log = LoggerFactory.getLogger(NotificationFunctions.class);

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email() {
        return accountsMsgDto -> {
            log.info("Sending email with the details "+ accountsMsgDto.email());
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto, Long> sms() {
        return accountsMsgDto -> {
            log.info("Sending sms with the details "+ accountsMsgDto.mobileNumber());
            return accountsMsgDto.accountNumber();
        };
    }
}
