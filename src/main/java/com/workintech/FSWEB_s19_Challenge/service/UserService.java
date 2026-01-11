package com.workintech.FSWEB_s19_Challenge.service;


import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.LoginRequest;
import com.workintech.FSWEB_s19_Challenge.dto.RegisterRequest;

public interface UserService {

    //Burada kullanici ;
    //-Register ve -Login olacak.Bu kontrolleri yapiyorum.
    //-GetCurrentUser ile (giris yapan kullanicinin islem yapabilmesi adina)
    //gerekli kontrolleri saglamak icin kullaniciyi donen method yaziyorum.


    User register(RegisterRequest registerRequest);

    String login(LoginRequest loginRequest);

    User getCurrentUser();
}
