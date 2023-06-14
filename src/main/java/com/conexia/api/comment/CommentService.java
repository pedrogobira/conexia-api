package com.conexia.api.comment;

import com.conexia.api.post.PostRepository;
import com.conexia.api.user.AuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthenticationService authenticationService;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository, AuthenticationService authenticationService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.authenticationService = authenticationService;
    }

    public List<CommentResponseDto> show(Long postId) {
        var comments = commentRepository.findByPostId(postId);
        var dtos = new ArrayList<CommentResponseDto>();
        for (Comment comment : comments) {
            var dto = new CommentResponseDto();
            BeanUtils.copyProperties(comment, dto);
            dto.setAuthorId(comment.getAuthor().getId());
            dto.setAuthorFirstName(comment.getAuthor().getFirstName());
            dto.setAuthorLastName(comment.getAuthor().getLastName());
            dto.setAuthorImage(comment.getAuthor().getImage());
            dtos.add(dto);
        }
        return dtos;
    }

    public void save(CommentRequestDto dto) {
        var post = postRepository.findById(dto.getPostId()).orElseThrow();
        var comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        var user = authenticationService.getLoggedInUser();
        comment.setAuthor(user);
        comment.setPost(post);
        commentRepository.save(comment);
    }
}

