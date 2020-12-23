package com.laurentiuspilca.ssia.security;

import com.laurentiuspilca.ssia.details.CustomUserDetails;
import com.laurentiuspilca.ssia.details.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Profile("jpa")
public class JpaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    @Qualifier("idToPasswordEncoder")
    private Map<String, PasswordEncoder> idToPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userName = authentication.getName();
        final String password = authentication.getCredentials().toString();

        System.out.println("CustomAuthenticationProvider.authenticate");

        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        DelegatingPasswordEncoder delegatingPasswordEncoder;
        delegatingPasswordEncoder = new DelegatingPasswordEncoder(userDetails.getUser().getAlgorithm().name(), idToPasswordEncoder);

        if (delegatingPasswordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
