package com.Twitter.org.mappers.Impl.TweetsMapper;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsDetailsDto;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetsDetailsDtoMapper implements Mapper<Tweets, TweetsDetailsDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TweetsDetailsDto mapTo(Tweets tweets) {
        return modelMapper.map(tweets, TweetsDetailsDto.class);
    }

    @Override
    public Tweets mapFrom(TweetsDetailsDto tweetsDetailsDto) {
        return modelMapper.map(tweetsDetailsDto, Tweets.class);
    }
}