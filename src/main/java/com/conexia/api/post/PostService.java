package com.conexia.api.post;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public PostResponseDto getById(Long id) {
        var post = postRepository.findById(id).orElseThrow();
        var dto = new PostResponseDto();
        BeanUtils.copyProperties(post, dto);
        dto.setAuthorId(post.getAuthor().getId());
        return dto;
    }

    public List<PostResponseDto> show(Long authorId) {
        var posts = postRepository.findAllByAuthorId(authorId);
        var dtos = new ArrayList<PostResponseDto>();
        for (Post post :
                posts) {
            var dto = new PostResponseDto();
            BeanUtils.copyProperties(post, dto);
            dto.setAuthorId(post.getAuthor().getId());
            dtos.add(dto);
        }
        return dtos;
    }


    @Transactional
    public boolean like(LikeRequestDto dto) {
        var post = postRepository.findById(dto.getPostId()).orElseThrow();
        var result = likeRepository.findByUserIdAndPostId(post.getId(), post.getAuthor().getId());

        if (result == null) {
            var like = new Like();
            like.setPost(post);
            like.setUser(post.getAuthor());
            likeRepository.save(like);
            return true;
        }

        return false;
    }
}

