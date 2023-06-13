package com.conexia.api.follow;

import com.conexia.api.exception.ConflictException;
import com.conexia.api.notification.NotifierFacade;
import com.conexia.api.user.AccountService;
import com.conexia.api.user.AuthenticationService;
import com.conexia.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    private final AccountService accountService;
    private final FollowerRepository followerRepository;
    private final AuthenticationService authenticationService;
    private final NotifierFacade notifierFacade;

    public FollowService(AccountService accountService, FollowerRepository followerRepository, AuthenticationService authenticationService,
                         NotifierFacade notifierFacade) {
        this.accountService = accountService;
        this.followerRepository = followerRepository;
        this.authenticationService = authenticationService;
        this.notifierFacade = notifierFacade;
    }

    @Transactional
    public Follower create(FollowRequestDto dto) {
        var followedDto = accountService.findById(dto.getFollowedId());
        var followed = new User();
        BeanUtils.copyProperties(followedDto, followed);
        followed.setId(dto.getFollowedId());

        var follower = authenticationService.getLoggedInUser();

        if (followerRepository.getByFollower_IdAndFollowed_Id(follower.getId(), followed.getId()).isPresent()) {
            throw new ConflictException();
        }

        var record = new Follower();
        record.setFollower(follower);
        record.setFollowed(followed);

        followerRepository.save(record);
        return record;
    }

    public void notifyFollowed(Follower follower) {
        notifierFacade.notify(follower.getFollower().getId(), follower.getFollowed().getId(), "Seguiu você");
    }
}
