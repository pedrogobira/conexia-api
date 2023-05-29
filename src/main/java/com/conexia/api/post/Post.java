package com.conexia.api.post;

import com.conexia.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean privacy;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    public Long getTotalLikes() {
        return (long) likes.size();
    }
}
