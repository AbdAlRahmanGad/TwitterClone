package com.Twitter.org.mappers.Impl.UserMapper;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserUpdateDto;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateMapper implements Mapper<User, UserUpdateDto> {

    private final ModelMapper modelMapper;

    public UserUpdateMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserUpdateDto mapTo(User user) {
        return modelMapper.map(user, UserUpdateDto.class);
    }

    @Override
    public User mapFrom(UserUpdateDto userUpdateDto) {
        return modelMapper.map(userUpdateDto, User.class);
    }
}