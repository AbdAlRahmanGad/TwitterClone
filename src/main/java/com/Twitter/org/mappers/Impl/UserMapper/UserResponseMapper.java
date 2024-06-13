package com.Twitter.org.mappers.Impl.UserMapper;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper implements Mapper<User, UserResponseDto> {

    private final ModelMapper modelMapper;

    public UserResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto mapTo(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public User mapFrom(UserResponseDto userResponseDto) {
        return modelMapper.map(userResponseDto, User.class);
    }
}