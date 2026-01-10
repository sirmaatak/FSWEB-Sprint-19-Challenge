package com.workintech.FSWEB_s19_Challenge.service;


public interface LikeService {

//http://localhost:3000/like/[POST] => Bir tweete bir kullanıcı tarafından like atılmasını sağlar.
//http://localhost:3000/dislike/[POST] => Bir tweete bir kullanıcı tarafından like atıldıysa bunun silinmesini sağlar.

    void like(Long tweetId);

    void dislike(Long tweetId);

}
