package com.workintech.FSWEB_s19_Challenge.dto;

import lombok.Data;

@Data
public class CommentRequest {

    private String content;
    private  Long tweetId;

}
