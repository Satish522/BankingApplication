package com.satish.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Class RequestTraceFilter
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 10/11/24
 */
@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders reqHttpHeaders = exchange.getRequest().getHeaders();
        if(isCorrelationIdPresent(reqHttpHeaders)){
            logger.debug("Banking correlation id found in RequestTracingFilter : {}", filterUtility.getCorrelationId(reqHttpHeaders));
        } else {
            String correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationId);
            logger.debug("Banking correlationdId generated in RequestTracingFilter : {}", correlationId);
        }
        return chain.filter(exchange);
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    private boolean isCorrelationIdPresent(HttpHeaders reqHttpHeaders) {
        if(filterUtility.getCorrelationId(reqHttpHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }
}
