package com.conexia.api.post;

import com.conexia.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "SELECT * FROM likes WHERE user_id = ?1 AND post_id = ?2", nativeQuery = true)
    Like findByUserIdAndPostId(Long userId, Long postId);
}
