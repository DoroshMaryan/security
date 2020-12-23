package com.laurentiuspilca.ssia.security;

import com.laurentiuspilca.ssia.details.CustomUserDetails;
import com.laurentiuspilca.ssia.entities.enums.EncryptionAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userName = authentication.getName();
        final String password = authentication.getCredentials().toString();

        Map<String, PasswordEncoder> idToPasswordEncoder = Map.of(EncryptionAlgorithm.BCRYPT.name(), bCryptPasswordEncoder,
                EncryptionAlgorithm.SCRYPT.name(), sCryptPasswordEncoder);

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
