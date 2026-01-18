package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.LoginRequest;
import com.workintech.FSWEB_s19_Challenge.dto.RegisterRequest;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

//    @Override
//    public User register(RegisterRequest registerRequest) {
//        if (userRepository.findByUserName(registerRequest.getFullName()) != null) {
//            throw new CustomException(
//                    "User already registered with this username",
//                    HttpStatus.BAD_REQUEST
//            );
//        }
//
//        User user = new User();
//        user.setFullName(registerRequest.getFullName());
//        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        return userRepository.save(user);
//    }

    @Override
    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

//    @Override
//    public String login(LoginRequest loginRequest) {
//
//        Authentication authentication =
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                loginRequest.getFullName(),
//                                loginRequest.getPassword()
//                        )
//                );
//
//        SecurityContextHolder.getContext()
//                .setAuthentication(authentication);
//
//        return "Login Successful for User : " + authentication.getName();
//    }

//    @Override
//    public String login(LoginRequest request) {
//
//        Authentication authentication =
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                request.getEmail(),
//                                request.getPassword()
//                        )
//                );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "Login successful";
//    }



//    @Override
//    public User getCurrentUser() {
//        String userName =
//                SecurityContextHolder.getContext()
//                        .getAuthentication()
//                        .getName();
//
//        return userRepository.findByUserName(userName);
//    }

    @Override
    public String login(LoginRequest request,
                        HttpServletRequest httpRequest,
                        HttpServletResponse httpResponse) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        new HttpSessionSecurityContextRepository()
                .saveContext(context, httpRequest, httpResponse);

        return "Login successful";
    }

    @Override
    public User getCurrentUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new CustomException("User not found", HttpStatus.NOT_FOUND));
    }




}

