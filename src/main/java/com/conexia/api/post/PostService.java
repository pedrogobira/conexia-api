package com.conexia.api.post;

import com.conexia.api.user.AccountService;
import com.conexia.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final AccountService accountService;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, AccountService accountService) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.accountService = accountService;
    }

    public PostResponseDto getById(Long id) {
        var post = postRepository.findById(id).orElseThrow();
        var dto = new PostResponseDto();
        BeanUtils.copyProperties(post, dto);
        return dto;
    }

    public List<PostResponseDto> show(Long authorId) {
        var posts = postRepository.findAllByAuthorId(authorId);
        var dtos = new ArrayList<PostResponseDto>();
        for (Post post :
                posts) {
            var dto = new PostResponseDto();
            BeanUtils.copyProperties(post, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public void save(PostRequestDto dto) {
        var post = new Post();
        var userDto = accountService.findById(dto.getAuthorId());
        BeanUtils.copyProperties(dto, post);
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(dto.getAuthorId());
        post.setAuthor(user);
        postRepository.save(post);
    }

    @Transactional
    public Long like(LikeRequestDto dto) {
        var post = postRepository.findById(dto.getPostId()).orElseThrow();
        var like = new Like();
        like.setPost(postRepository.findById(dto.getPostId()).orElseThrow());
        var userDto = accountService.findById(dto.getUserId());
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(dto.getUserId());
        like.setUser(user);
        likeRepository.save(like);
        return post.getTotalLikes();
    }
}

