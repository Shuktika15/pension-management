package dev.shuktika.apigateway.filter;


import dev.shuktika.apigateway.exception.AuthenticationTokenNotFoundException;
import dev.shuktika.apigateway.exception.BadTokenException;
import dev.shuktika.apigateway.model.PensionerDetails;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Autowired
    public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new AuthenticationTokenNotFoundException("Authorization token not found");
            }

            String authorizationHeader =
                    Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String authenticationUrl = config.getAuthenticationUrl();

            log.info("AuthenticationFilter.GatewayFilter apply(Config config): Authorization Token: {}", authorizationHeader);

            return webClientBuilder
                    .build()
                    .get()
                    .uri(authenticationUrl + "/authorization/authenticate")
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> Mono.error(new BadTokenException("Bad token")))
                    .bodyToMono(PensionerDetails.class)
                    .map(pensionerDetails -> {
                        log.info("{}", pensionerDetails);
                        return exchange;
                    })
                    .flatMap(chain::filter);
        };
    }

    @Data
    public static class Config {
        private String authenticationUrl;
    }

}
