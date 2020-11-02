package guru.springframework.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenMapper {
    private String access_token;
    private String id;
    private String userName;
    private String name;
}
