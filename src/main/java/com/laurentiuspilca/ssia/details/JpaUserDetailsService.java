package com.laurentiuspilca.ssia.details;

import com.laurentiuspilca.ssia.entities.User;
import com.laurentiuspilca.ssia.reposetories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Problem during authentication!"));

        return new CustomUserDetails(user);

    }
}
