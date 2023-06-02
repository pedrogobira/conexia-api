package com.conexia.api.follow;

import com.conexia.api.user.AccountService;
import com.conexia.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    private final AccountService accountService;
    private final FollowerRepository followerRepository;

    public FollowService(AccountService accountService, FollowerRepository followerRepository) {
        this.accountService = accountService;
        this.followerRepository = followerRepository;
    }

    @Transactional
    public boolean create(FollowRequestDto dto) {
        var followerDto = accountService.findById(dto.getFollowerId());
        var followedDto = accountService.findById(dto.getFollowedId());

        var follower = new User();
        var followed = new User();

        BeanUtils.copyProperties(followerDto, follower);
        BeanUtils.copyProperties(followedDto, followed);

        follower.setId(dto.getFollowerId());
        followed.setId(dto.getFollowedId());

        var record = new Follower();
        record.setFollower(follower);
        record.setFollowed(followed);

        followerRepository.save(record);

        return true;
    }

    public void notifyFollowed() {
    }
}
