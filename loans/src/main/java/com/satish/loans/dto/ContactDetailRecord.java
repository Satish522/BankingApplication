package com.satish.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * Class ContactDetailRecord
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 01/06/24
 */
@Getter
@Setter
@ConfigurationProperties("loans")
public class ContactDetailRecord {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> callSupport;
}
