package com.conexia.api.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> getFollowedsByFollower_Id(Long id);

    void deleteByFollower_IdAndFollowed_Id(Long followerId, Long followedId);
}
