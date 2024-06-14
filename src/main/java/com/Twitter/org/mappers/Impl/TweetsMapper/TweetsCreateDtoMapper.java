package com.Twitter.org.mappers.Impl.TweetsMapper;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;
import com.Twitter.org.mappers.Mapper;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TweetsCreateDtoMapper implements Mapper<Tweets, TweetsCreateDto> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        // if the model mapper does not have the mapping configuration, add it
        if (modelMapper.getTypeMap(TweetsCreateDto.class, Tweets.class) == null) {
            PropertyMap<TweetsCreateDto, Tweets> tweetsMap = new PropertyMap<>() {
                protected void configure() {
                    // skip the id field, since it is auto-generated and to avoid the model mapper to map it
                    skip(destination.getId());
                }
            };
            modelMapper.addMappings(tweetsMap);
        }
    }

    @Override
    public TweetsCreateDto mapTo(Tweets tweets) {
        return modelMapper.map(tweets, TweetsCreateDto.class);
    }

    @Override
    public Tweets mapFrom(TweetsCreateDto tweetsCreateDto) {
        return modelMapper.map(tweetsCreateDto, Tweets.class);
    }
}
