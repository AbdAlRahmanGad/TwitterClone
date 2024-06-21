package com.Twitter.org.Models.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserResponseDto {

    private String userName;

    private String firstName;

    private String lastName;

    private String bio;

    private byte[] profilePic;

    private byte[] coverPic;

    private LocalDate dateJoined;
}
