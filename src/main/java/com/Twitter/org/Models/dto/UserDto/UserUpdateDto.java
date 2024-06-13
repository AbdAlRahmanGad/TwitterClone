package com.Twitter.org.Models.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String userName;

    private String firstName;

    private String lastName;

    private String bio;

    private byte[] profilePic;

    private byte[] coverPic;
}
