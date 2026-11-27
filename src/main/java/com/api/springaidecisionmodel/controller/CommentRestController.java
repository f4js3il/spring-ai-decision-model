package com.api.springaidecisionmodel.controller;


import com.api.springaidecisionmodel.payload.Comment;
import com.api.springaidecisionmodel.payload.Moderation;
import com.api.springaidecisionmodel.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<Moderation> call(@RequestBody Comment comment) {
        Moderation moderation = commentService.getCommentDetails(comment.text());
        return ResponseEntity.ok(moderation);
    }
}
