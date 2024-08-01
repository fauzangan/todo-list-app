package todo_list_app.todo_list_app.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo_list_app.todo_list_app.model.User;
import todo_list_app.todo_list_app.repository.UserRepository;
import todo_list_app.todo_list_app.service.UserService;
import todo_list_app.todo_list_app.utils.dto.UserDTO;
import todo_list_app.todo_list_app.utils.helper.DateFormatter;
import todo_list_app.todo_list_app.utils.response.UserResponse;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> UserResponse.builder()
                        .id(String.valueOf(user.getId()))
                        .role(user.getRoles().toString())
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
                .role(user.getRoles().toString())
                .createdAt(DateFormatter.convertDateToString(user.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }
}
