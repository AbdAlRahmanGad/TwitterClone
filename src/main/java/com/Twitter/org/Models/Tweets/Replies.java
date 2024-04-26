package com.Twitter.org.Models.Tweets;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Replies")
@IdClass(Replies.RepliesId.class)
public class Replies {

    @Id
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Id
    @Column(name = "reply_id", nullable = false)
    private Integer replyId;

    @Data
    @NoArgsConstructor
    public static class RepliesId implements Serializable {
        private Integer postId;
        private Integer replyId;
    }
}