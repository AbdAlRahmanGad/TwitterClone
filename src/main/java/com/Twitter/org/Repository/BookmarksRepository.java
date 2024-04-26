package com.Twitter.org.Repository;

import com.Twitter.org.Models.Bookmarks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends CrudRepository<Bookmarks, Integer>{

    // TODO() Add a query to get all the bookmarks of a user
    // TODO() Add a query to check if a user has bookmarked a tweet
    // TODO() Add a query to delete a bookmark
    // TODO() Add a query to delete all bookmarks of a user
    // TODO() Add a query to add a bookmark
}