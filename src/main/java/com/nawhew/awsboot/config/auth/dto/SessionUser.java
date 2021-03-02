package com.nawhew.awsboot.config.auth.dto;

import com.nawhew.awsboot.user.domain.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보를 담습니다.
 */
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
