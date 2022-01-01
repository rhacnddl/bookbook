package org.gorany.bootbook.config.auth;

import lombok.RequiredArgsConstructor;
import org.gorany.bootbook.api.domain.user.User;
import org.gorany.bootbook.api.domain.user.UserRepository;
import org.gorany.bootbook.config.auth.dto.OAuthAttributes;
import org.gorany.bootbook.config.auth.dto.SessionUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
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
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository repository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * 현재 로그인 진행 중인 서비스를 구분하는 코드
         * 지금은 구글만 사용하는 불필요한 값이나, 추후 네이버 로그인 연동 시
         * 네이버/구글 로그인인지 구분하기 위함
         * */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /**
         * OAuth2 로그인 진행 시 키가 되는 필드값을 의미. (PK)
         * 구글의 경우 기본적으로 코드를 지원하나, 네이버/카카오는 기본 지원하지 않음
         * 구글의 기본 코드는 sub이다.
         * 추후 네이버/구글 로그인을 동시 지원할 때 사용된다.
         * */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
            .getUserNameAttributeName();

        /**
         * OAuth2UserService를 통해 가져온 OAuth2User의 속성들을 담을 클래스
         * 추후 네이버 등 다른 소셜 로그인도 이 클래스를 사용한다.
         * */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
            oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        /**
         * Session에 사용자 정보를 저장하기 위한 DTO
         * */
        session.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
            Collections.singleton(
                new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = repository.findByEmail(attributes.getEmail()).map(en -> en.update(attributes.getEmail(),
            attributes.getPicture())).orElse(attributes.toEntity());

        return repository.save(user);
    }
}
