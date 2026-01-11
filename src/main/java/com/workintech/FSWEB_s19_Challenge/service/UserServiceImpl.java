package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.LoginRequest;
import com.workintech.FSWEB_s19_Challenge.dto.RegisterRequest;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.findByUserName(registerRequest.getFullName()) != null) {
            throw new CustomException(
                    "User already registered with this username",
                    HttpStatus.BAD_REQUEST
            );
        }

        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getFullName(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return "Login Successful for User : " + authentication.getName();
    }

    @Override
    public User getCurrentUser() {
        String userName =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByUserName(userName);
    }
}

