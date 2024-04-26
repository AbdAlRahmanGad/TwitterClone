CREATE TABLE twitter_user
(
    user_name character varying(32) NOT NULL,
    bio character varying(255) DEFAULT '',
    cover_pic bytea DEFAULT NULL,
    profile_pic bytea DEFAULT NULL,
    first_name character varying(40) NOT NULL,
    last_name character varying(40) NOT NULL,
    date_joined date NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (user_name)
);

CREATE TABLE followers
(
    user_name   varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    follower_id varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    PRIMARY KEY (follower_id,
                 user_name)
);

CREATE TABLE following
(
    user_name    varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    following_id varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    PRIMARY KEY (following_id,
                 user_name)
);

CREATE TABLE mutes
(
    user_name varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    muted_id  varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    PRIMARY KEY (muted_id,
                 user_name)
);
CREATE TABLE blockedBy
(
    user_name varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    who_blocked_me varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    PRIMARY KEY (user_name,
                 who_blocked_me)
);
CREATE TABLE blocked
(
    user_name varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    whom_i_blocked varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    PRIMARY KEY (user_name,
                 whom_i_blocked)
);
CREATE TABLE tweets
(
    id serial NOT NULL,
    date_tweeted timestamp DEFAULT LOCALTIMESTAMP(0),
    parent_id integer DEFAULT -1,
    author_id varchar(32) NOT NULL,
    content varchar(255) DEFAULT '',
    media bytea DEFAULT NULL,
    bookmarks_number integer DEFAULT 0,
    replies_number integer DEFAULT 0,
    repost_number integer DEFAULT 0,
    likes_number integer DEFAULT 0,
    is_repost boolean DEFAULT FALSE,
    original_post integer DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE likes
(
    tweet_id   integer NOT NULL REFERENCES tweets (id),
    username   varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    date_liked timestamp DEFAULT LOCALTIMESTAMP(0),
    PRIMARY KEY (tweet_id,
                 username)
);
CREATE TABLE replies
(
    post_id  integer NOT NULL REFERENCES tweets (id),
    reply_id integer NOT NULL REFERENCES tweets (id),
    PRIMARY KEY (post_id,
                 reply_id)
);
CREATE TABLE bookmarks
(
    tweet_id integer NOT NULL REFERENCES tweets (id),
    username varchar(32) NOT NULL REFERENCES twitter_user (user_name),
    date_bookmarked timestamp DEFAULT LOCALTIMESTAMP(0),
    PRIMARY KEY (tweet_id,
                 username)
);