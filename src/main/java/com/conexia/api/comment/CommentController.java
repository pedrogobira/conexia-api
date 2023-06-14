package com.conexia.api.comment;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentResponseDto>> show(@PathVariable(value = "id") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.show(postId));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid CommentRequestDto dto) {
        commentService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
