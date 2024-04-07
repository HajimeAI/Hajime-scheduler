package ai.hajimebot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInfo {
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;
    private String expires;
}
