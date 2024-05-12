package com.Twitter.org.mappers.Impl;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TweetsMapper implements Mapper<Tweets, TweetsDto> {

    private ModelMapper modelMapper;

    public TweetsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TweetsDto mapTo(Tweets tweets) {
        return modelMapper.map(tweets, TweetsDto.class);
    }

    @Override
    public Tweets mapFrom(TweetsDto tweetsDto) {
        return modelMapper.map(tweetsDto, Tweets.class);
    }
}
