package com.Twitter.org.services.UserDetails;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.findUserByUsername(username));
        if (user.isPresent()) {
            var userObj = user.get();
            Set<GrantedAuthority> authorities = userObj.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toSet());
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getPassword())
                    .authorities(authorities)
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
