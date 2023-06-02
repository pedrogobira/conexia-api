package com.conexia.api.follow;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecommendService {
    private final FollowerRepository followerRepository;

    public RecommendService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    public RecommendResponseDto recommend(Long userId) {
        var followeds = followerRepository.getFollowedsByFollower_Id(userId);

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
