package com.Twitter.org.Models.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blocked")
public class Blocked {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Id
    @Column(name = "whom_i_blocked", nullable = false)
    private String whomIBlocked;

    public Blocked(User userName, User whomIBlocked) {
        this.userName = userName.getUserName();
        this.whomIBlocked = whomIBlocked.getUserName();
    }
}