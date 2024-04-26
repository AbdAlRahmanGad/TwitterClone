package com.Twitter.org.Models.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "twitter_user")
public class User {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "bio")
    private String bio;

//    @Lob
    @Column(name = "profile_pic")
    private byte[] profilePic;

//    @Lob
    @Column(name = "cover_pic")
    private byte[] coverPic;

    @Column(name = "date_joined", nullable = false)
    private LocalDate dateJoined = LocalDate.now();

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "Following",
//            joinColumns = @JoinColumn(name = "user_name"),
//            inverseJoinColumns = @JoinColumn(name = "following_id"))
//    private Set<User> followingUsers = new HashSet<>();
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "Mutes",
//            joinColumns = @JoinColumn(name = "user_name"),
//            inverseJoinColumns = @JoinColumn(name = "muted_id"))
//    private Set<User> mutedUsers = new HashSet<User>();
}