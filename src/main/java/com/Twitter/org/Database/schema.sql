CREATE TABLE twitter_user
(
    user_name   character varying(32)  NOT NULL,
    password    character varying(255) NOT NULL,
    bio         character varying(255)          DEFAULT '',
    cover_pic   bytea                           DEFAULT NULL,
    profile_pic bytea                           DEFAULT NULL,
    first_name  character varying(40)  NOT NULL,
    last_name   character varying(40)  NOT NULL,
    date_joined date                   NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (user_name)
);

CREATE TABLE following
(
    follower             varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    followed             varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    following_start_date timestamp NOT NULL,
    PRIMARY KEY (follower, followed)
);

CREATE TABLE mutes
(
    user_name varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    muted_id  varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    PRIMARY KEY (muted_id,
                 user_name)
);
CREATE TABLE blocked
(
    user_name      varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    whom_i_blocked varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    PRIMARY KEY (user_name,
                 whom_i_blocked)
);
CREATE TABLE tweets
(
    id               serial,
    date_tweeted     timestamp    DEFAULT LOCALTIMESTAMP(0),
    parent_id        integer      DEFAULT NULL REFERENCES tweets (id) ON DELETE CASCADE,
    author_id        varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    content          varchar(255) DEFAULT '',
    media            bytea        DEFAULT NULL,
    bookmarks_number integer      DEFAULT 0,
    replies_number   integer      DEFAULT 0,
    repost_number    integer      DEFAULT 0,
    likes_number     integer      DEFAULT 0,
    is_repost        boolean      DEFAULT FALSE,
    is_comment       boolean      DEFAULT FALSE,
    original_post    integer      DEFAULT NULL REFERENCES tweets (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
CREATE TABLE likes
(
    tweet_id   integer     NOT NULL REFERENCES tweets (id) ON DELETE CASCADE,
    username   varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE, -- for simplicity
    date_liked timestamp DEFAULT LOCALTIMESTAMP(0),
    PRIMARY KEY (tweet_id,
                 username)
);
CREATE TABLE replies
(
    post_id  integer NOT NULL REFERENCES tweets (id) ON DELETE CASCADE,
    reply_id integer NOT NULL REFERENCES tweets (id) ON DELETE CASCADE,
    PRIMARY KEY (post_id,
                 reply_id)
);
CREATE TABLE bookmarks
(
    tweet_id        integer     NOT NULL REFERENCES tweets (id) ON DELETE CASCADE,
    username        varchar(32) NOT NULL REFERENCES twitter_user (user_name) ON DELETE CASCADE,
    date_bookmarked timestamp DEFAULT LOCALTIMESTAMP(0),
    PRIMARY KEY (tweet_id,
                 username)
);

-- TODO: Usernames Uniqueness:
-- ALTER TABLE twitter_user ADD CONSTRAINT unique_username UNIQUE (user_name);

-----------------

-- TODO: Indexes:
-- CREATE INDEX idx_author_id ON tweets(author_id);
-- AND SO ON...

-----------------

-- TODO: Separate Media Table
-- CREATE TABLE media
-- (
--     media_id   SERIAL PRIMARY KEY,
--     media_data BYTEA       NOT NULL,
--     media_type VARCHAR(32) NOT NULL, -- e.g., 'profile_pic', 'cover_pic', 'tweet_media'
--     user_name  VARCHAR(32) REFERENCES twitter_user (user_name) ON DELETE CASCADE,
--     tweet_id   INTEGER REFERENCES tweets (id) ON DELETE CASCADE
-- );

-----------------
-- TODO: Retweets/Quotes in a Separate Table (Not Sure)
-- TODO: Add Reposted Date Column
-- ALTER TABLE tweets ADD COLUMN reposted_date TIMESTAMP DEFAULT NULL;

-----------------

-- TODO: Role Management
-- CREATE TABLE roles
-- (
--     role_id   serial PRIMARY KEY,
--     role_name varchar(32) UNIQUE NOT NULL
-- );
--
-- ALTER TABLE twitter_user
--     ADD COLUMN role_id integer REFERENCES roles (role_id);

-----------------

-- TODO: Separate Engagement Metrics
-- CREATE TABLE tweet_engagements
-- (
--     tweet_id integer NOT NULL REFERENCES tweets(id) ON DELETE CASCADE,
--     bookmarks_number integer DEFAULT 0,
--     replies_number integer DEFAULT 0,
--     repost_number integer DEFAULT 0,
--     likes_number integer DEFAULT 0,
--     PRIMARY KEY (tweet_id)
-- );

-- Then, remove bookmarks_number, replies_number, repost_number, likes_number from tweets table
