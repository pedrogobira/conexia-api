package com.conexia.api.follow;

import com.conexia.api.user.AccountService;
import com.conexia.api.user.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UnfollowService {
    private final FollowerRepository followerRepository;
    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    public UnfollowService(FollowerRepository followerRepository, AccountService accountService, AuthenticationService authenticationService) {
        this.followerRepository = followerRepository;
        this.accountService = accountService;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public void unfollow(FollowRequestDto dto) {
        var follower = authenticationService.getLoggedInUser();
        var followedDto = accountService.findById(dto.getFollowedId());
        followerRepository.deleteByFollower_IdAndFollowed_Id(follower.getId(), dto.getFollowedId());
    }
}
