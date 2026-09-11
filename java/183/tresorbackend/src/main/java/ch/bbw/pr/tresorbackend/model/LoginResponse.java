package ch.bbw.pr.tresorbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginResponse
 *   Data-transfer-object, response to client in case of a login request.
 *   Extended with JWT tokens and 2FA support.
 * @author Peter Rutschmann
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String role;
    private boolean requires2FA;
    private String tempToken;

    // Backward compatibility constructor
    public LoginResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }
}
