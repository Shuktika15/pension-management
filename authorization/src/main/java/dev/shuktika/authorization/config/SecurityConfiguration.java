package dev.shuktika.authorization.config;

import dev.shuktika.authorization.filter.AuthenticationFilter;
import dev.shuktika.authorization.filter.AuthorizationFilter;
import dev.shuktika.authorization.filter.ExceptionHandlerFilter;
import dev.shuktika.authorization.service.PensionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final PensionerService pensionerService;
    private final PasswordEncoder passwordEncoder;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final PropertyValuesConfiguration propertyValuesConfiguration;

    @Bean
    public UserDetailsService userDetailsService() {
        return pensionerService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(pensionerService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), propertyValuesConfiguration);
        authenticationFilter.setFilterProcessesUrl("/authorization/login");
        return authenticationFilter;
    }

    private AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(propertyValuesConfiguration);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http.authorizeRequests()
                .antMatchers(propertyValuesConfiguration.getAuthWhitelistAsStringArray())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authenticationFilter())
                .addFilterBefore(exceptionHandlerFilter, LogoutFilter.class)
                .addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
