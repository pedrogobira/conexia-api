package com.conexia.api.post;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final PosterFacade posterFacade;

    public PostController(PostService postService, PosterFacade posterFacade) {
        this.postService = postService;
        this.posterFacade = posterFacade;
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<PostResponseDto>> show(@PathVariable(value = "id") Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.show(authorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PostRequestDto dto) {
        posterFacade.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/like")
    public ResponseEntity<Object> like(@RequestBody @Valid LikeRequestDto dto) {
        var result = postService.like(dto);
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
