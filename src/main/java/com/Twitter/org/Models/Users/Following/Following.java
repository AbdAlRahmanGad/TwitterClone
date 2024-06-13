package com.Twitter.org.Models.Users.Following;

import com.Twitter.org.Models.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Following")
@IdClass(FollowingId.class)
public class Following {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower", referencedColumnName = "user_name")
    private User follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed", referencedColumnName = "user_name")
    private User followed;

    // TODO: Add date following started
}