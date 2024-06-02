package com.satish.cards.dto;

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
 * @since 26/05/24
 */
@ConfigurationProperties("cards")
public record ContactDetailRecord(String message, Map<String, String> contactDetails, List<String> callSupport) {
}
