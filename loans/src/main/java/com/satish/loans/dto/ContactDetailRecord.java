package com.satish.loans.dto;

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
@ConfigurationProperties("loans")
public record ContactDetailRecord(String message, Map<String, String> contactDetails, List<String> callSupport) {
}
