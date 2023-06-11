package com.conexia.api.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> getFollowedsByFollower_Id(Long id);

    void deleteByFollower_IdAndFollowed_Id(Long followerId, Long followedId);

    Optional<Follower> getByFollower_IdAndFollowed_Id(Long followerId, Long followedId);
}
