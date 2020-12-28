package com.laurentiuspilca.ssia.config;

import com.laurentiuspilca.ssia.details.InMemoryUserDetailsService;
import com.laurentiuspilca.ssia.filters.AuthenticationLoggingFilter;
import com.laurentiuspilca.ssia.filters.RequestValidationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
@Profile("inMemory")
public class InMemorySecurityProjectConfig extends WebSecurityConfigurerAdapter {

    private final Logger LOG = LoggerFactory.getLogger(InMemorySecurityProjectConfig.class);

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        var user1 = User.withUsername("john")
                .password("11111")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("11111")
                .authorities("ROLE_MANAGER")
                .build();

        return new InMemoryUserDetailsService(List.of(user1, user2));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOG.info("Configure type authentication: {}", "httpBasic");
        http.httpBasic();
        http.csrf().disable();
        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class);
        final String expression = "hasRole('ADMIN')";
        http.authorizeRequests()
                .antMatchers("/hello").access(expression)
                .regexMatchers(".*/(us|uk|ca)+/(en|fr)").hasRole("MANAGER")
                .mvcMatchers("/ciao").hasRole("MANAGER")
                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
                .mvcMatchers(HttpMethod.POST, "/a").permitAll()
                .mvcMatchers("/a/b/**").authenticated()
                .anyRequest().permitAll();

//        http.authorizeRequests().anyRequest().access(expression);
//        http.authorizeRequests().anyRequest().hasAuthority("WRITE");
//        http.authorizeRequests().anyRequest().access("hasAuthority('WRITE')");
    }
}