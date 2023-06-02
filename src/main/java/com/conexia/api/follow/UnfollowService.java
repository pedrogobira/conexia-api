package com.conexia.api.follow;

import com.conexia.api.user.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UnfollowService {
    private final FollowerRepository followerRepository;
    private final AccountService accountService;

    public UnfollowService(FollowerRepository followerRepository, AccountService accountService) {
        this.followerRepository = followerRepository;
        this.accountService = accountService;
    }

    @Transactional
    public void unfollow(FollowRequestDto dto) {
        var followerDto = accountService.findById(dto.getFollowerId());
        var followedDto = accountService.findById(dto.getFollowedId());
        followerRepository.deleteByFollower_IdAndFollowed_Id(dto.getFollowerId(), dto.getFollowedId());
    }
}
