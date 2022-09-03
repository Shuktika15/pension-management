package dev.shuktika.authorization.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "authorization")
@Data
public class PropertyValuesConfiguration {
    private Map<String, String> jwt;
    private Set<String> authWhitelist;

    public String getSecretKey() {
        return jwt.get("secret-key");
    }

    public String[] getAuthWhitelistAsStringArray() {
        return authWhitelist.toArray(String[]::new);
    }

    public boolean isWhiteListed(String url) {
        return authWhitelist.stream()
                .anyMatch(s -> {
                    String requestedUrl = url.substring(1);
                    String testUrl = "";

                    if (requestedUrl.contains("/")) {
                        String[] endpoints = requestedUrl.split("/");

                        if (endpoints.length > 1 && endpoints[0].equalsIgnoreCase("authorization")) {
                            testUrl = endpoints[1];
                        }
                    } else {
                        testUrl = url;
                    }

                    return s.contains(testUrl);
                });
    }
}
