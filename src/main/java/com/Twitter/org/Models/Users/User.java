package com.Twitter.org.Models.Users;

import com.Twitter.org.Models.Users.Blocks.Blocks;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@JdbcTypeCode(Types.VARBINARY)
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


    @JdbcType(VarbinaryJdbcType.class)
    //@Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "profile_pic")
    private byte[] profilePic;

    //    @Lob
    @Column(name = "cover_pic")
    private byte[] coverPic;

    @Column(name = "date_joined", nullable = false)
    private LocalDate dateJoined = LocalDate.now();

    @OneToMany(mappedBy = "blocker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blocks> blockedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "blocked", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blocks> blockedByUsers = new ArrayList<>();

}