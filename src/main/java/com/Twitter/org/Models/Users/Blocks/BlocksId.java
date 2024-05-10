package com.Twitter.org.Models.Users.Blocks;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlocksId implements Serializable {

    private String blocker;

    private String blocked;
}