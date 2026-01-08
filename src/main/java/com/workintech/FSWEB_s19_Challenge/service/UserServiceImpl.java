package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.Entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.LoginRequest;
import com.workintech.FSWEB_s19_Challenge.dto.RegisterRequest;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest registerRequest) {
        if(userRepository.findByUserName(registerRequest.getFullName())!=null){
            //TODO : throw exception because this user is already register
        }
        User user=new User();
        user.setFullName(registerRequest.getFullName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return  userRepository.save(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User user=userRepository.findByUserName(loginRequest.getFullName());
        if(user==null){
            //TODO : throw exception because this user doesnt exist
            throw new CustomException("This user is already exist");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(),loginRequest.getPassword())){
            //TODO : throw exception because this user dont match the user that i found
            throw new CustomException("This user not found");
        }

        return "Login Successful for User : " + user.getFullName();
    }

    @Override
    public User getCurrentUser() {
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(userName);
    }
}
