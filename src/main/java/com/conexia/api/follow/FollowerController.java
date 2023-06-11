package com.conexia.api.follow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowerController {
    private final FollowFacade followFacade;
    private final UnfollowService unfollowService;
    private final RecommendService recommendService;

    public FollowerController(FollowFacade followFacade, UnfollowService unfollowService, RecommendService recommendService) {
        this.followFacade = followFacade;
        this.unfollowService = unfollowService;
        this.recommendService = recommendService;
    }

    @PostMapping
    public ResponseEntity<Object> follow(@RequestBody FollowRequestDto dto) {
        followFacade.follow(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Object> unfollow(@RequestBody FollowRequestDto dto) {
        unfollowService.unfollow(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/recommend")
    public ResponseEntity<RecommendResponseDto> recommend() {
        return ResponseEntity.status(HttpStatus.OK).body(recommendService.recommend());
    }
}
