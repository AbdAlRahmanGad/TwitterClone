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
@Table(name = "Following")
public class Following {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User followingUser;


    public Following(User userName, User followingUser) {
        this.userName = userName.getUserName();
        this.followingUser = followingUser;
    }

}