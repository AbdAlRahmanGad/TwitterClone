package com.Twitter.org.Models.Users;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Following")
@IdClass(FollowingId.class)
public class Following {
    @Id
    @Column(name = "user_name")
    private String userName;

    @Id
    @Column(name = "following_id")
    private String followingId;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", insertable = false, updatable = false)
    private User user;
}