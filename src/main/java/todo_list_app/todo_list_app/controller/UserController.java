package todo_list_app.todo_list_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todo_list_app.todo_list_app.service.UserService;
import todo_list_app.todo_list_app.utils.response.PageResponse;
import todo_list_app.todo_list_app.utils.response.UserResponse;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public PageResponse<UserResponse> getAllUser(Pageable pageable){
        return new PageResponse<>(userService.getAllUser(pageable));
    }

    @GetMapping("/users/{id}")
    public UserResponse getOneUser(@PathVariable Integer id){
        return userService.getOneUser(id);
    }
}
