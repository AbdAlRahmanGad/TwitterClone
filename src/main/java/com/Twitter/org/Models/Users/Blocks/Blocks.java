package com.Twitter.org.Models.Users.Blocks;

import com.Twitter.org.Models.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocked")
@IdClass(BlocksId.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blocks {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User blocker;

    @Id
    @ManyToOne
    @JoinColumn(name = "whom_i_blocked", referencedColumnName = "user_name")
    private User blocked;
}