package org.gorany.bootbook.config.auth.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gorany.bootbook.api.domain.user.User;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        name = user.getName();
        email = user.getEmail();
        picture = user.getPicture();
    }
}
