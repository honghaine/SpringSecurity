package com.restAPI.api;


import com.restAPI.config.JwtProvider;
import com.restAPI.model.AppUser;
import com.restAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterRequest {
    @Autowired
    public UserService userService;
    @Autowired
    public JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder;

    public RegisterRequest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/token")
    public String getToken(@RequestBody AppUser appUser) throws Exception {
        UserDetails userDetails = userService.loadUserByUsername(appUser.getUsername());
        if (passwordEncoder.matches(appUser.getPassword(), userDetails.getPassword())) {
            return jwtProvider.generateToken(appUser.getUsername());
        }
        throw new Exception("User details invalid");
    }

    @PostMapping("/signup")
    public AppUser createUser(@RequestBody AppUser req) throws Exception {
        return userService.createUser(req);
    }
}
