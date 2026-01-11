package com.workintech.FSWEB_s19_Challenge.controller;

import com.workintech.FSWEB_s19_Challenge.entity.Comment;
import com.workintech.FSWEB_s19_Challenge.dto.CommentRequest;
import com.workintech.FSWEB_s19_Challenge.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Comment create(@RequestBody CommentRequest commentRequest) {
        return commentService.create(commentRequest.getTweetId(), commentRequest);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        return commentService.update(id, commentRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }

}
