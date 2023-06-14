package com.conexia.api.follow;

import com.conexia.api.user.AuthenticationService;
import com.conexia.api.user.User;
import com.conexia.api.user.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendService {
    private final FollowerRepository followerRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public RecommendService(FollowerRepository followerRepository, AuthenticationService authenticationService,
            UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    public List<RecommendResponseDto> recommend() {
        var user = authenticationService.getLoggedInUser();

        var listUsers = userRepository.findByCity(user.getCity());
        var listRecommend = new ArrayList<RecommendResponseDto>();

        var count = 0;

        for (User listUser : listUsers) {
            if(count == 10) {
                break;
            }

            if(listUser.getId().equals(user.getId())) {
                continue;
            }

            listRecommend.add(RecommendResponseDto.builder().userId(listUser.getId()).firstName(listUser.getFirstName())
                    .lastName(listUser.getLastName()).image(listUser.getImage()).build());

            count += 1;
        }

        return listRecommend;
    }
}
