package com.Twitter.org.mappers.Impl;

import com.Twitter.org.Models.Users.Following.Following;
import com.Twitter.org.Models.dto.FollowingDto;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class FollowingMapper implements Mapper<Following, FollowingDto> {
    private final ModelMapper modelMapper;

    public FollowingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FollowingDto mapTo(Following following) {
        return modelMapper.map(following, FollowingDto.class);
    }

    @Override
    public Following mapFrom(FollowingDto followingDto) {
        return modelMapper.map(followingDto, Following.class);
    }
}