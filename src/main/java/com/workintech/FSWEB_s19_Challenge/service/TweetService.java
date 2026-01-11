package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.dto.TweetRequest;

import java.util.List;

public interface TweetService {
//+http://localhost:3000/tweet[POST] => Tweet oluşturma ve veritabanına kaydetme.
//   TODO:  Tweet'in hangi kullanıcıya ait olduğu mutlaka tutulmalıdır. Anonym tweetler olmamalıdır.
//http://localhost:3000/tweet/findByUserId[GET] => Bir kullanıcının tüm tweetlerini getirmelidir.
//+http://localhost:3000/tweet/findById[GET] => Bir tweet için tüm bilgilerini getirmelidir.
//+http://localhost:3000/tweet/:id[PUT] => Bir tweet üzerinde değiştirelecek kısımları update etmek için kullanılmalıdır.
//+http://localhost:3000/tweet/:id[DELETE] => Id bilgisi verilen tweeti silmek için kullanılır.
//TODO: (Sadece tweet sahibi ilgili tweeti silebilimelidir.)

    Tweet create(TweetRequest tweetRequest);
    Tweet update(Long id,TweetRequest tweetRequest);
    void delete(Long id);
    Tweet findById(Long id);
    List<Tweet> findByUserId(Long userId);



}
