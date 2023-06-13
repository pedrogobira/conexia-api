package com.conexia.api.follow;

import org.springframework.stereotype.Service;

@Service
public class FollowFacade {
    private final FollowService service;

    public FollowFacade(FollowService service) {
        this.service = service;
    }

    public void follow(FollowRequestDto dto) {
        var follower = service.create(dto);
        service.notifyFollowed(follower);
    }
}
