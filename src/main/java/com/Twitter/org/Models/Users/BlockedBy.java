package com.Twitter.org.Models.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "blockedBy")
public class BlockedBy {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Id
    @Column(name = "who_blocked_me", nullable = false)
    private String whoBlockedMe;

    public BlockedBy(User userName, User whoBlockedMe) {
        this.userName = userName.getUserName();
        this.whoBlockedMe = whoBlockedMe.getUserName();
    }
}