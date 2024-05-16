package com.Twitter.org.Models.Users.Mutes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MutesId implements Serializable {
    private String userName;
    private String mutedId;
}
