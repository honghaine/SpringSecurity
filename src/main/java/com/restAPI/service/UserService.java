package com.restAPI.service;

import com.restAPI.model.AppUser;
import com.restAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username or password is incorrect"));

        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public AppUser createUser(AppUser req) throws Exception {
        String username = req.getUsername();
        String password = req.getPassword();

        boolean isExist = userRepository.existsByUsername(username);
        if(isExist) {
            throw new Exception("User already exists");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        if (username.contains("hai")) {
            appUser.setRole("ROLE_EDITOR");
        }
        appUser.setRole("ROLE_ADMIN");

        return userRepository.save(appUser);
    }
}
