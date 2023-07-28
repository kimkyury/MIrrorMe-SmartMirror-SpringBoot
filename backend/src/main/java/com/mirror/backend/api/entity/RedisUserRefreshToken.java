package com.mirror.backend.api.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("refreshToken")
public class RedisUserRefreshToken {

    @Id
    private String userId;
    private String refreshToken;

    public RedisUserRefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

}
