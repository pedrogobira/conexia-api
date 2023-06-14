package com.conexia.api.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private String authorImage;
    private String body;
    private LocalDate date;
    private Boolean privacy;
    private Long totalLikes;
    private String image;
}
