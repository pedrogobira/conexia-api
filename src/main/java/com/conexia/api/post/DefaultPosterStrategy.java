package com.conexia.api.post;

import com.conexia.api.user.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DefaultPosterStrategy implements Poster {
    private final PostRepository postRepository;

    private final AuthenticationService authenticationService;

    public DefaultPosterStrategy(PostRepository postRepository, AuthenticationService authenticationService) {
        this.postRepository = postRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Post make(PostRequestDto dto) {
        var post = new Post();
        BeanUtils.copyProperties(dto, post);
        var user = authenticationService.getLoggedInUser();
        post.setAuthor(user);
        return post;
    }

    @Transactional
    @Override
    public void save(Post post) {
        postRepository.save(post);
    }
}
