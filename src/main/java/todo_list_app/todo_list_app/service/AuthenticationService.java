package todo_list_app.todo_list_app.service;

import todo_list_app.todo_list_app.utils.dto.AuthDTO.AuthenticationResponse;
import todo_list_app.todo_list_app.utils.dto.AuthDTO.RegisterRequest;
import todo_list_app.todo_list_app.utils.dto.AuthDTO.AuthenticationRequest;
import todo_list_app.todo_list_app.utils.dto.AuthDTO.RefreshTokenResponse;
import todo_list_app.todo_list_app.utils.dto.UserDTO;
import todo_list_app.todo_list_app.utils.request.RefreshTokenRequest;

public interface AuthenticationService {
    UserDTO register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshToken) throws Exception;
}
