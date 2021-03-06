package com.nawhew.awsboot.config.auth;

import com.nawhew.awsboot.config.auth.dto.SessionUser;
import com.nawhew.awsboot.user.domain.User;
import com.nawhew.awsboot.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuthAttributes attributes = getOAuthAttributes(userRequest);

        User user = this.saveOrUpdate(attributes);
        this.httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey()))
                                            , attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private OAuthAttributes getOAuthAttributes(OAuth2UserRequest userRequest) {
        return OAuthAttributes.of(userRequest);
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = this.userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity())
                ;
        return this.userRepository.save(user);
    }
}
