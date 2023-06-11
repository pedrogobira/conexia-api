package com.conexia.api.follow;

import com.conexia.api.user.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecommendService {
    private final FollowerRepository followerRepository;
    private final AuthenticationService authenticationService;

    public RecommendService(FollowerRepository followerRepository, AuthenticationService authenticationService) {
        this.followerRepository = followerRepository;
        this.authenticationService = authenticationService;
    }

    public RecommendResponseDto recommend() {
        var user = authenticationService.getLoggedInUser();
        var followeds = followerRepository.getFollowedsByFollower_Id(user.getId());

        if (followeds.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Collections.shuffle(followeds);
        var followed = followeds.get(0).getFollowed();

        var followedsByFollowed = followerRepository.getFollowedsByFollower_Id(followed.getId());

        if (followedsByFollowed.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Collections.shuffle(followeds);
        var recommendation = followedsByFollowed.get(0).getFollowed();

        return RecommendResponseDto
                .builder()
                .userId(recommendation.getId())
                .firstName(recommendation.getFirstName())
                .lastName(recommendation.getLastName())
                .build();
    }
}
