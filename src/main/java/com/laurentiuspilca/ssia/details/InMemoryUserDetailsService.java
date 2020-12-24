package com.laurentiuspilca.ssia.details;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class InMemoryUserDetailsService implements UserDetailsService {
    private final static Logger LOG = LoggerFactory.getLogger(InMemoryUserDetailsService.class);
    private final List<UserDetails> users;

    public InMemoryUserDetailsService(List<UserDetails> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return users.stream()
                .filter(u -> u.getUsername().equals(userName))
                .findAny()
                .orElseThrow(() -> {
                    LOG.error("User {} is not found", userName);
                    return new UsernameNotFoundException("User not found");
                });
    }
}
