package com.conexia.api.follow;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResponseDto {
    @NotBlank
    private Long userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
