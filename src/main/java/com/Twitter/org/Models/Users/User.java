package com.Twitter.org.Models.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.time.LocalDate;

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

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "Following",
//            joinColumns = @JoinColumn(name = "user_name"),
//            inverseJoinColumns = @JoinColumn(name = "following_id"))
//    private List<User> followingUsers = new ArrayList<>();
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "Mutes",
//            joinColumns = @JoinColumn(name = "user_name"),
//            inverseJoinColumns = @JoinColumn(name = "muted_id"))
//    private Set<User> mutedUsers = new HashSet<User>();
}