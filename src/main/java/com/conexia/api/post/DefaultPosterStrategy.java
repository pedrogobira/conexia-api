package com.conexia.api.post;

import com.conexia.api.user.AccountService;
import com.conexia.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DefaultPosterStrategy implements Poster {
    private final AccountService accountService;
    private final PostRepository postRepository;

    public DefaultPosterStrategy(AccountService accountService, PostRepository postRepository) {
        this.accountService = accountService;
        this.postRepository = postRepository;
    }

    @Override
    public Post make(PostRequestDto dto) {
        var post = new Post();
        var userDto = accountService.findById(dto.getAuthorId());
        BeanUtils.copyProperties(dto, post);
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(dto.getAuthorId());
        post.setAuthor(user);
        return post;
    }

    @Transactional
    @Override
    public void save(Post post) {
        postRepository.save(post);
    }
}
