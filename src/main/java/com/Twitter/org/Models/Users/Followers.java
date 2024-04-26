package com.Twitter.org.Models.Users;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Builder
@Table(name = "Followers")
public class Followers {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    public Followers(User userName, User followingUser) {
        this.userName = userName.getUserName();
        this.follower = followingUser;
    }


}