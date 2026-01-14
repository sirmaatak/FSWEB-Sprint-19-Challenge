package com.workintech.FSWEB_s19_Challenge.controller;

import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.dto.TweetRequest;
import com.workintech.FSWEB_s19_Challenge.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public Tweet create(@RequestBody TweetRequest tweetRequest){
        return tweetService.create(tweetRequest);
    }

    @PutMapping("/{id}")
    public Tweet update(@PathVariable Long id,@RequestBody TweetRequest tweetRequest){
        return tweetService.update(id,tweetRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        tweetService.delete(id);
    }

    @GetMapping("/findById/{id}")
    public Tweet findById(@PathVariable Long id){
        return tweetService.findById(id);
    }

    @GetMapping("/findByUserId/{userId}")
    public List<Tweet> findByUserId(@PathVariable Long userId){
        return tweetService.findByUserId(userId);
    }
}
