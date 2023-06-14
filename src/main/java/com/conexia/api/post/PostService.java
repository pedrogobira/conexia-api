package com.conexia.api.post;

import com.conexia.api.user.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final AuthenticationService authenticationService;

    public PostService(PostRepository postRepository, LikeRepository likeRepository,
                       AuthenticationService authenticationService) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.authenticationService = authenticationService;
    }

    public PostResponseDto getById(Long id) {
        var post = postRepository.findById(id).orElseThrow();

        if (post.getPrivacy() != null && post.getPrivacy() && !authenticationService.getLoggedInUser().getId()
                .equals(post.getAuthor().getId())) {
            throw new NoSuchElementException();
        }

        var dto = new PostResponseDto();
        BeanUtils.copyProperties(post, dto);
        dto.setAuthorId(post.getAuthor().getId());
        dto.setAuthorFirstName(post.getAuthor().getFirstName());
        dto.setAuthorLastName(post.getAuthor().getLastName());
        dto.setAuthorImage(post.getAuthor().getImage());
        return dto;
    }

    public List<PostResponseDto> show(Long authorId) {
        var posts = postRepository.findAllByAuthorId(authorId);
        var dtos = new ArrayList<PostResponseDto>();
        for (Post post : posts) {
            if (post.getPrivacy() != null && post.getPrivacy() && !authenticationService.getLoggedInUser().getId()
                    .equals(post.getAuthor().getId())) {
                continue;
            }
            var dto = new PostResponseDto();
            BeanUtils.copyProperties(post, dto);
            dto.setAuthorId(post.getAuthor().getId());
            dto.setAuthorFirstName(post.getAuthor().getFirstName());
            dto.setAuthorLastName(post.getAuthor().getLastName());
            dto.setAuthorImage(post.getAuthor().getImage());
            dtos.add(dto);
        }
        return dtos;
    }


    @Transactional
    public boolean like(LikeRequestDto dto) {
        var post = postRepository.findById(dto.getPostId()).orElseThrow();
        var user = authenticationService.getLoggedInUser();
        var result = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());

        if (post.getPrivacy() != null && post.getPrivacy() && !authenticationService.getLoggedInUser().getId()
                .equals(post.getAuthor().getId())) {
            throw new NoSuchElementException();
        }

        if (result == null) {
            var like = new Like();
            like.setPost(post);
            like.setUser(post.getAuthor());
            likeRepository.save(like);
            return true;
        }

        return false;
    }

    public List<PostResponseDto> showAll() {
        var posts = postRepository.findAll();
        var dtos = new ArrayList<PostResponseDto>();
        for (Post post : posts) {
            if (post.getPrivacy() != null && post.getPrivacy() && !authenticationService.getLoggedInUser().getId()
                    .equals(post.getAuthor().getId())) {
                continue;
            }
            var dto = new PostResponseDto();
            BeanUtils.copyProperties(post, dto);
            dto.setAuthorId(post.getAuthor().getId());
            dto.setAuthorFirstName(post.getAuthor().getFirstName());
            dto.setAuthorLastName(post.getAuthor().getLastName());
            dto.setAuthorImage(post.getAuthor().getImage());
            dtos.add(dto);
        }
        return dtos;
    }
}

