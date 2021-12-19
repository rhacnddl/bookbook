package org.gorany.bootbook.config.auth.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gorany.bootbook.api.domain.user.Role;
import org.gorany.bootbook.api.domain.user.User;

@Getter
@AllArgsConstructor
@Builder
public class OAuthAttributes {

    private static final String NAVER = "naver";

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId,
        String userNameAttributeName,
        Map<String, Object> attributes) {

        if (NAVER.equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때.
     * 가입할 때 기본 권한을 GUEST로 주자.
     * OAuthAttributes 클래스 생성이 끝나면 같은 패키지에 SessionUser를 생성한다.
     * */
    public User toEntity() {
        return User.builder()
            .name(name)
            .email(email)
            .picture(picture)
            .role(Role.GUEST)
            .build();
    }
}
