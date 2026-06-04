package ch.bbw.pr.tresorbackend.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotEmpty(message="Token is required.")
    private String token;

    @NotEmpty (message="Password is required.")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
            message="Password requirements 8 to 20 characters, one uppercase, one lowercase, one number, one special sign: @#$%^&+=!, no whitespace")
    private String password;

    @NotEmpty (message="Password-confirmation is required.")
    private String passwordConfirmation;
}
