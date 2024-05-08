package com.Twitter.org.initializer;

import com.Twitter.org.services.Impl.BookmarksServiceImpl;
import com.Twitter.org.services.Impl.LikesServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StartupDataInitializer {

    @Autowired
    private LikesServiceImpl likesService;

    @Autowired
    private BookmarksServiceImpl bookmarksService;

    // TODO: ADD Missing Counters here

    @PostConstruct
    public void initializeData() {
        likesService.updateLikesCountersOnStartup();
        bookmarksService.updateBookmarksCountersOnStartup();
    }
}