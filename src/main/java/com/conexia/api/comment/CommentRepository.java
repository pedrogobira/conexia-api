package com.conexia.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);

    @Query(value = "SELECT count(*) FROM comments WHERE post_id = ?1", nativeQuery = true)
    Long countCommentsByPostId(Long postId);
}
