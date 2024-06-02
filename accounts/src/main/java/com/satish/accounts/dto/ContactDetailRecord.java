package com.satish.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;
import java.util.Map;

/**
 * Class ContactDetailRecord
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 26/05/24
 */

@Getter
@Setter
@ConfigurationProperties("accounts")
public class ContactDetailRecord {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> callSupport;
}
