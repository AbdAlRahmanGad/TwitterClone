package com.Twitter.org.Models.Users.Following;

import com.Twitter.org.Models.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @CreationTimestamp
    @Column(name = "following_start_date")
    private LocalDateTime followingStartDate;

    public Following(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

    // IDE generated equals and hashcode
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Following following = (Following) o;
        return getFollower() != null && Objects.equals(getFollower(), following.getFollower())
               && getFollowed() != null && Objects.equals(getFollowed(), following.getFollowed());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(follower, followed);
    }
}