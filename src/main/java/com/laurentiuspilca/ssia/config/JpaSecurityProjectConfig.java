package com.laurentiuspilca.ssia.config;

import com.laurentiuspilca.ssia.details.JpaUserDetailsService;
import com.laurentiuspilca.ssia.entities.enums.EncryptionAlgorithm;
import com.laurentiuspilca.ssia.security.JpaAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Map;

@Configuration
@EnableAsync
@Profile("jpa")
public class JpaSecurityProjectConfig extends WebSecurityConfigurerAdapter {

    private final Logger LOG = LoggerFactory.getLogger(JpaSecurityProjectConfig.class);

    @Bean
    public JpaUserDetailsService jpaUserDetailsService() {
        return new JpaUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new JpaAuthenticationProvider(jpaUserDetailsService());
    }

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Bean
    @Qualifier("idToPasswordEncoder")
    public Map<String, PasswordEncoder> getIdToPasswordEncoder() {
        return Map.of(EncryptionAlgorithm.BCRYPT.name(), bCryptPasswordEncoder(),
                EncryptionAlgorithm.SCRYPT.name(), sCryptPasswordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOG.info("Configure type authentication: {}", "formLogin");
        http.formLogin()
                .defaultSuccessUrl("/main", true);

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);
    }
}
