package com.shell.webapplication.auth.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface CustomUserDetailsService extends UserDetailsService {

    public static final String cacheName = "auth_cache";

    @Override
    @Cacheable(value = cacheName, key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
