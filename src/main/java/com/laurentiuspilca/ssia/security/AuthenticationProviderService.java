package com.laurentiuspilca.ssia.security;

import com.laurentiuspilca.ssia.details.CustomUserDetails;
import com.laurentiuspilca.ssia.entities.enums.EncryptionAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

        System.out.println("CustomAuthenticationProvider.authenticate");

        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (userDetails.getUser().getAlgorithm().equals(EncryptionAlgorithm.BCRYPT)) {
            return checkPassword(userDetails, password, bCryptPasswordEncoder);
        } else if (userDetails.getUser().getAlgorithm().equals(EncryptionAlgorithm.SCRYPT)) {
            return checkPassword(userDetails, password, sCryptPasswordEncoder);
        } else {
            throw new AuthenticationServiceException("bad EncryptionAlgorithm");
        }
    }

    private Authentication checkPassword(final CustomUserDetails user, final String rawPassword, final PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
