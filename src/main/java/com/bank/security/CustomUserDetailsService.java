package com.bank.security;

import com.bank.model.MyUser;
import com.bank.repository.dao.UserDAO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userDAO.getUserByUsername(username);

        if (myUser == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + myUser.getRole()));

        return User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
