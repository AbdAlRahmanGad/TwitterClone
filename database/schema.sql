CREATE TABLE Following (
    user_name TEXT NOT NULL,
    following_id TEXT NOT NULL,
    PRIMARY KEY (user_name, following_id)
);

CREATE TABLE Followers (
    user_name TEXT NOT NULL,
    follower_id TEXT NOT NULL,
    PRIMARY KEY (user_name, follower_id)
);


CREATE TABLE User (
    user_name TEXT PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    bio TEXT DEFAULT '',
    profile_pic BLOB DEFAULT NULL,
    cover_pic BLOB DEFAULT NULL,
    date_joined DATE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Blocks (
    blocker_id TEXT NOT NULL,
    blocked_id TEXT NOT NULL,
    PRIMARY KEY (blocker_id, blocked_id)
);

CREATE TABLE Mutes (
    user_name TEXT NOT NULL,
    muted_id TEXT NOT NULL,
    PRIMARY KEY (user_name, muted_id)
);

CREATE TABLE Likes (
    tweet_id INTEGER NOT NULL,
    username TEXT NOT NULL,
    date_liked DATE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (tweet_id, username)
);

CREATE TABLE Bookmarks (
    tweet_id INTEGER NOT NULL,
    username TEXT NOT NULL,
    date_bookmarked DATE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (tweet_id, username)
);

CREATE TABLE Tweets (
    id INTEGER PRIMARY KEY,
    date_tweeted DATE DEFAULT CURRENT_TIMESTAMP,
    author_id TEXT NOT NULL,
    parent_id INTEGER DEFAULT -1,
    content TEXT DEFAULT '',
    media BLOB DEFAULT NULL,
    bookmarks_number INTEGER DEFAULT 0,
    replies_number INTEGER DEFAULT 0,
    likes_number INTEGER DEFAULT 0,
    repost_number INTEGER DEFAULT 0,
    is_repost BOOLEAN DEFAULT FALSE,
    original_post INTEGER DEFAULT NULL
);

CREATE TABLE Replies (
    post_id INTEGER,
    reply_id INTEGER,
    PRIMARY KEY (post_id, reply_id),
    FOREIGN KEY (post_id) REFERENCES Tweets (id),
    FOREIGN KEY (reply_id) REFERENCES Tweets (id)
);