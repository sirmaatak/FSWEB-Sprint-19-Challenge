package com.workintech.FSWEB_s19_Challenge.controller;


import com.workintech.FSWEB_s19_Challenge.dto.LikeDislikeRetweetRequest;
import com.workintech.FSWEB_s19_Challenge.service.RetweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
@RequiredArgsConstructor
public class RetweetController {


    private final RetweetService retweetService;


    @PostMapping
    public ResponseEntity<String> retweet(@RequestBody LikeDislikeRetweetRequest request) {
        retweetService.retweet(request.getTweetId());
        return ResponseEntity.ok("Tweet retweeted successfully");
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> undoRetweet(@PathVariable Long id) {
        retweetService.undoRetweet(id);
        return ResponseEntity.ok("Retweet removed successfully");
    }
}

