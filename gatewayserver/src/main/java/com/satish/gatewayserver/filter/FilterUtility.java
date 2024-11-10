package com.satish.gatewayserver.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * Class FilterUtility
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 10/11/24
 */
@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "banking-correlation-id";

    public String getCorrelationId(HttpHeaders reqHttpHeaders) {
        if(reqHttpHeaders.get(CORRELATION_ID) != null) {
            List<String> requestHeaderList = reqHttpHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        }
        return null;
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    private ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }
}
