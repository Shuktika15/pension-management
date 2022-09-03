package dev.shuktika.authorization.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("ExceptionHandlerFilter.doFilterInternal " +
                            "(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) : {}",
                    e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
