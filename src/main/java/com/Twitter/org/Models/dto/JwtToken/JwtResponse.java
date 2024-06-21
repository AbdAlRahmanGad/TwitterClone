package com.Twitter.org.Models.dto.JwtToken;

import lombok.Data;

@Data
public class JwtResponse
{
    private String accessToken;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
