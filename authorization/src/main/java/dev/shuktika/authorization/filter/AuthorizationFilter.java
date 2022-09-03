package dev.shuktika.authorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.shuktika.authorization.config.PropertyValuesConfiguration;
import dev.shuktika.authorization.exception.InvalidJWTException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final PropertyValuesConfiguration propertyValuesConfiguration;

    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestUrl = request.getServletPath();
        boolean whiteListedRequest = propertyValuesConfiguration.isWhiteListed(requestUrl);

        if (!whiteListedRequest) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (token != null && token.startsWith("Bearer ")) {
                try {
                    token = token.replace("Bearer ", "");
                    var algorithm = Algorithm.HMAC512(
                            propertyValuesConfiguration.getSecretKey().getBytes(StandardCharsets.UTF_8)
                    );
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    var decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    var usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } catch (JWTVerificationException e) {
                    var errorMsg = String.format(
                            "AuthenticationFilter.doFilterInternal " +
                                    "(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) : " +
                                    "%s",
                            e.getMessage()
                    );
                    log.error(errorMsg);
                    throw new InvalidJWTException(errorMsg);
                }
            } else {
                var errorMsg = String.format("AuthenticationFilter.doFilterInternal " +
                        "(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) : " +
                        "Bad token on url %s", requestUrl);
                log.error(errorMsg);
                throw new InvalidJWTException(errorMsg);
            }
        }

        filterChain.doFilter(request, response);
    }
}
