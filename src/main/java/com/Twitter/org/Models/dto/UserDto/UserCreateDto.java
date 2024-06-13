package com.Twitter.org.Models.dto.UserDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String bio;

    private byte[] profilePic;

    private byte[] coverPic;

    private LocalDate dateJoined = LocalDate.now();
}
