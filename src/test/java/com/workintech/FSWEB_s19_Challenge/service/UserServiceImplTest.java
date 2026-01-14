package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.dto.LoginRequest;
import com.workintech.FSWEB_s19_Challenge.dto.RegisterRequest;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authManager;

    @Test
    void register(){

        RegisterRequest request = new RegisterRequest();
        request.setFullName("Sirma Atak");
        request.setEmail("test@mail.com");
        request.setPassword("123456");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encoded-password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFullName(request.getFullName());
        savedUser.setEmail(request.getEmail());
        savedUser.setPassword("encoded-password");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result =userService.register(request);

        assertNotNull(result);
        assertEquals(request.getEmail(),result.getEmail());
        assertNotEquals(request.getPassword(),result.getPassword());
        assertEquals(request.getFullName(),result.getFullName());
        assertEquals(savedUser.getPassword(), result.getPassword());

    }

    @Test
    void login(){
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setFullName("Sirma Atak");
        loginRequest.setEmail("test@mail.com");
        loginRequest.setPassword("123456");

        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);


        String result=userService.login(loginRequest,null,null);

        assertNotNull(result);
        assertEquals("Login successful",result);
        verify(authManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
