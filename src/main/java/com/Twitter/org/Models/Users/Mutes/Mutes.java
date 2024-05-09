package com.Twitter.org.Models.Users.Mutes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mutes")
@IdClass(MutesId.class)
public class Mutes {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Id
    @Column(name = "muted_id", nullable = false)
    private String mutedId;
}