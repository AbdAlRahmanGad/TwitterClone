-- Mock data for following table
INSERT INTO following (follower, followed)
VALUES
    ('user1', 'user2'),
    ('user1', 'user3'),
    ('user2', 'user1'),
    ('user2', 'user4'),
    ('user3', 'user1'),
    ('user3', 'user2'),
    ('user4', 'user3'),
    ('user5', 'user1'),
    ('user5', 'user4');
/*
This mock data represents the following relationships:

user1 is following user2 and user3
user2 is following user1 and user4
user3 is following user1 and user2
user4 is following user3
user5 is following user1 and user4
 */

-- Mock data for tweets table
INSERT INTO tweets (id, date_tweeted, parent_id, author_id, content, media, bookmarks_number, replies_number, repost_number, likes_number, is_repost, is_comment, original_post)
VALUES
    (1, '2023-03-15 10:00:00', NULL, 'user1', 'Excited to start my new job at ABC today!', NULL, 0, 0, 0, 0, FALSE, FALSE, NULL),
    (2, '2023-03-15 10:10:00', 1, 'user2', 'Congrats on the new job, @user1! Wishing you all the best.', NULL, 0, 0, 0, 0, FALSE, TRUE, NULL),
    (3, '2023-03-15 10:15:00', 1, 'user3', 'Good luck on your first day, @user1! You''ll do great.', NULL, 0, 0, 0, 0, FALSE, TRUE, NULL),
    (4, '2023-03-15 11:00:00', NULL, 'user2', 'Just finished a great run in the park. Feeling energized for the rest of the day!', NULL, 0, 0, 0, 0, FALSE, FALSE, NULL),
    (5, '2023-03-15 11:10:00', 4, 'user1', 'Nice one, @user2! Running is such a great way to start the day.', NULL, 0, 0, 0, 0, FALSE, TRUE, NULL),
    (6, '2023-03-15 12:00:00', NULL, 'user3', 'Sharing some interesting insights on data science in my latest blog post. Check it out! https://example.com', NULL, 0, 0, 0, 0, FALSE, FALSE, NULL),
    (7, '2023-03-15 12:10:00', 6, 'user4', 'Great read, @user3! Loved the section on machine learning.', NULL, 0, 0, 0, 0, FALSE, TRUE, NULL),
    (8, '2023-03-15 13:00:00', NULL, 'user5', 'Excited to attend the music festival this weekend. Can''t wait to see my favorite bands!', NULL, 0, 0, 0, 0, FALSE, FALSE, NULL),
    (9, '2023-03-15 13:10:00', NULL, 'user1', 'Retweeting @user3''s great blog post on data science. https://example.com', NULL, 0, 0, 0, 0, TRUE, FALSE, 6);

/*
In this example, tweet 9 is a retweet of tweet 6, so the original_post column for tweet 9 is set to the ID of tweet 6 (i.e., 6).
The is_repost column for tweet 9 is also set to TRUE to indicate that it is a retweet.
In the `tweets` table mock data, the `parent_id` column is used to represent the relationship between tweets and their replies.
If a tweet is a reply to another tweet, its `parent_id` column will contain the ID of the tweet it is replying to.
For example, in the mock data I provided earlier, tweet 2 is a reply to tweet 1, so its `parent_id` column is set to 1.
Similarly, tweet 5 is a reply to tweet 4, so its `parent_id` column is set to 4.
If a tweet is not a reply to another tweet (i.e., it is an original tweet), its `parent_id` column will be `NULL`.
For example, tweets 1, 3, 4, 6, and 9 in the mock data are all original tweets, so their `parent_id` columns are all `NULL`.
The `parent_id` column can be used to query the database and retrieve a tweet and all of its replies, or to retrieve all of the original tweets posted by a particular user.
 */