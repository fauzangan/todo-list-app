package todo_list_app.todo_list_app.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todo_list_app.todo_list_app.model.Role;
import todo_list_app.todo_list_app.model.User;
import todo_list_app.todo_list_app.repository.UserRepository;
import todo_list_app.todo_list_app.service.UserService;
import todo_list_app.todo_list_app.utils.dto.UserDTO;
import todo_list_app.todo_list_app.utils.helper.DateFormatter;
import todo_list_app.todo_list_app.utils.request.RoleRequest;
import todo_list_app.todo_list_app.utils.request.SuperAdminRequest;
import todo_list_app.todo_list_app.utils.response.UserResponse;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> UserResponse.builder()
                        .id(String.valueOf(user.getId()))
                        .role(user.getRole().toString())
                        .username(user.getRealUsername())
                        .email(user.getEmail())
                        .createdAt(DateFormatter.convertDateToString(user.getCreatedAt(), "yyyy-MM-dd"))
                        .build());
    }

    @Override
    public UserResponse getOneUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getRealUsername())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .createdAt(DateFormatter.convertDateToString(user.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }

    @Override
    public UserResponse updateRoleUser(Integer id, RoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Role newRole;
        try {
            newRole = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role specified");
        }

        user.setRole(newRole);

        User updatedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(String.valueOf(updatedUser.getId()))
                .username(updatedUser.getRealUsername())
                .email(updatedUser.getEmail())
                .role(String.valueOf(updatedUser.getRole()))
                .createdAt(DateFormatter.convertDateToString(updatedUser.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }

    @Override
    public UserResponse createSuperAdmin(SuperAdminRequest request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_SUPER_ADMIN)
                .build();

        User savedUser = userRepository.save(newUser);

        return UserResponse.builder()
                .id(String.valueOf(savedUser.getId()))
                .username(savedUser.getRealUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().toString())
                .createdAt(DateFormatter.convertDateToString(savedUser.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }
}
