package com.workintech.FSWEB_s19_Challenge.controller;


import com.workintech.FSWEB_s19_Challenge.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;



    //ornek kullanimi : .....like?tweetId=10
    @PostMapping("/like")
    public void like(@RequestParam Long tweetId) {
        likeService.like(tweetId);
    }

    //ornek kullanimi : .....dislike?tweetId=10
    @PostMapping("/dislike")
    public void dislike(@RequestParam Long tweetId) {
        likeService.dislike(tweetId);
    }

}
