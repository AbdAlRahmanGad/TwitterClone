package com.Twitter.org.Models.Tweets;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepliesId implements Serializable {
    private Integer post;

    private Integer reply;
}
