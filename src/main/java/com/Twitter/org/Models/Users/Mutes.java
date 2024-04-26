package com.Twitter.org.Models.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Mutes")
public class Mutes {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Id
    @Column(name = "muted_id", nullable = false)
    private String mutedId;

    public Mutes(User userName, User mutedUser) {
        this.userName = userName.getUserName();
        this.mutedId = mutedUser.getUserName();
    }

}