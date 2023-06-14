package com.conexia.api.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private String authorImage;
    private String body;
    private LocalDate date;
}
