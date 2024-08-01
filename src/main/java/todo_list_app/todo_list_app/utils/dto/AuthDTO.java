package todo_list_app.todo_list_app.utils.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

public class AuthDTO {

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationRequest {

        @NotBlank(message = "Email cannot be empty")
        private String email;

        @NotBlank(message = "Password cannot be empty")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshTokenResponse {
        private String accessToken;
    }

}
