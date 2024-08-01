package todo_list_app.todo_list_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import todo_list_app.todo_list_app.service.ToDoService;
import todo_list_app.todo_list_app.service.UserService;
import todo_list_app.todo_list_app.utils.request.RoleRequest;
import todo_list_app.todo_list_app.utils.request.SuperAdminRequest;
import todo_list_app.todo_list_app.utils.response.PageResponse;
import todo_list_app.todo_list_app.utils.response.TodoAdminResponse;
import todo_list_app.todo_list_app.utils.response.UserResponse;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ToDoService toDoService;

    @GetMapping("/users")
    public PageResponse<UserResponse> getAllUser(Pageable pageable){
        return new PageResponse<>(userService.getAllUser(pageable));
    }

    @GetMapping("/users/{id}")
    public UserResponse getOneUser(@PathVariable Integer id){
        return userService.getOneUser(id);
    }


    @PatchMapping("/users/{id}/role")
    @Secured("ROLE_SUPER_ADMIN")
    public ResponseEntity<UserResponse> changeUserRole(
            @PathVariable Integer id,
            @RequestBody RoleRequest request,
            @RequestHeader("X-Admin-Secret-Key") String secretKey
    ) {
        if (!"admin turing machine alpha beta".equals(secretKey)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserResponse updatedUser = userService.updateRoleUser(id, request);

        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @PostMapping("/super-admin")
    @Secured("ROLE_SUPER_ADMIN")
    public ResponseEntity<UserResponse> createSuperAdmin(
            @RequestBody SuperAdminRequest request,
            @RequestHeader("X-Super-Admin-Secret-Key") String secretKey
    ) {
        if (!"superman admin turing machine gigachad sigma".equals(secretKey)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserResponse createdUser = userService.createSuperAdmin(request);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/todos")
    public PageResponse<TodoAdminResponse> getAllTodos(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title,
            Pageable pageable) {
        return new PageResponse<>(toDoService.getAll(userId, status, title, pageable));
    }

    @GetMapping("/todos/{id}")
    public TodoAdminResponse getOneTodo(@PathVariable Integer id){
        return toDoService.getOneByIdTodoAdmin(id);
    }
}
