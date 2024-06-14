package com.Twitter.org.mappers.Impl.TweetsMapper;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsSummaryDto;
import com.Twitter.org.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetsSummaryDtoMapper implements Mapper<Tweets, TweetsSummaryDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TweetsSummaryDto mapTo(Tweets tweets) {
        return modelMapper.map(tweets, TweetsSummaryDto.class);
    }

    @Override
    public Tweets mapFrom(TweetsSummaryDto tweetSummaryDto) {
        return modelMapper.map(tweetSummaryDto, Tweets.class);
    }
}