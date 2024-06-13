package com.Twitter.org.mappers.Impl.UserMapper;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserCreateDto;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserCreateMapper implements Mapper<User, UserCreateDto> {

    private final ModelMapper modelMapper;

    public UserCreateMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserCreateDto mapTo(User user) {
        return modelMapper.map(user, UserCreateDto.class);
    }

    @Override
    public User mapFrom(UserCreateDto userCreateDto) {
        return modelMapper.map(userCreateDto, User.class);
    }
}