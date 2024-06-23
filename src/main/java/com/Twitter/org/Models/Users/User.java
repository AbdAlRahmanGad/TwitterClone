package com.Twitter.org.Models.Users;

import com.Twitter.org.Models.Users.Blocks.Blocks;
import com.Twitter.org.Models.Users.Following.Following;
import com.Twitter.org.Models.Users.Roles.Role;
import com.Twitter.org.Models.dto.UserDto.UserCreateDto;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.Models.dto.UserDto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "password", nullable = false)
    private String password;


    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "cover_pic")
    private byte[] coverPic;

    @Column(name = "date_joined", nullable = false)
    private LocalDate dateJoined = LocalDate.now();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Following> following = new ArrayList<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Following> followers = new ArrayList<>();


    @OneToMany(mappedBy = "blocker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blocks> blockedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "blocked", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blocks> blockedByUsers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(UserCreateDto userCreateDto) {
        this.userName = userCreateDto.getUserName();
        this.password = userCreateDto.getPassword();
        this.firstName = userCreateDto.getFirstName();
        this.lastName = userCreateDto.getLastName();
        this.bio = userCreateDto.getBio();
        this.profilePic = userCreateDto.getProfilePic();
        this.coverPic = userCreateDto.getCoverPic();
        this.dateJoined = userCreateDto.getDateJoined();
    }

    public UserResponseDto toResponseDto() {
        return UserResponseDto.builder()
                .userName(this.userName)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .bio(this.bio)
                .profilePic(this.profilePic)
                .coverPic(this.coverPic)
                .dateJoined(this.dateJoined)
                .build();
    }

    public void updateFromDto(UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getUserName() != null) {
            // TODO: Implement to cascade the change to other tables
        }

        if (userUpdateDto.getFirstName() != null) {
            this.firstName = userUpdateDto.getFirstName();
        }
        if (userUpdateDto.getLastName() != null) {
            this.lastName = userUpdateDto.getLastName();
        }
        if (userUpdateDto.getBio() != null) {
            this.bio = userUpdateDto.getBio();
        }
        if (userUpdateDto.getProfilePic() != null) {
            this.profilePic = userUpdateDto.getProfilePic();
        }
        if (userUpdateDto.getCoverPic() != null) {
            this.coverPic = userUpdateDto.getCoverPic();
        }
    }
}