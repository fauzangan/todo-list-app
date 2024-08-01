package todo_list_app.todo_list_app.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todo_list_app.todo_list_app.model.User;
import todo_list_app.todo_list_app.repository.UserRepository;
import todo_list_app.todo_list_app.security.JwtService;
import todo_list_app.todo_list_app.service.AuthenticationService;
import todo_list_app.todo_list_app.utils.dto.AuthDTO;
import todo_list_app.todo_list_app.utils.dto.AuthDTO.AuthenticationResponse;
import todo_list_app.todo_list_app.utils.dto.AuthDTO.AuthenticationRequest;
import todo_list_app.todo_list_app.utils.dto.AuthDTO.RegisterRequest;
import todo_list_app.todo_list_app.utils.dto.UserDTO;
import todo_list_app.todo_list_app.utils.request.RefreshTokenRequest;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplement implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDTO register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = repository.save(user);

        return UserDTO.builder()
                .id(String.valueOf(savedUser.getId()))
                .username(savedUser.getRealUsername())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            return new AuthenticationResponse(accessToken, refreshToken);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public AuthDTO.RefreshTokenResponse refreshToken(RefreshTokenRequest refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtService.extractUsername(refreshToken.getRefreshToken());
        UserDetails userDetails = repository.findByEmail(email)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found for the given token"));

        String newAccessToken = jwtService.generateToken(userDetails);
        return new AuthDTO.RefreshTokenResponse(newAccessToken);
    }
}
