package com.conexia.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
        private String email;
        private String firstName;
        private String lastName;
        private LocalDate birthday;
        private ColorName favouriteColour;
        private String city;
}
