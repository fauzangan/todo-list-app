package todo_list_app.todo_list_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import todo_list_app.todo_list_app.utils.request.RoleRequest;
import todo_list_app.todo_list_app.utils.request.SuperAdminRequest;
import todo_list_app.todo_list_app.utils.response.UserResponse;

public interface UserService {
    Page<UserResponse> getAllUser(Pageable pageable);
    UserResponse getOneUser(Integer id);
    UserResponse updateRoleUser(Integer id, RoleRequest request);
    UserResponse createSuperAdmin(SuperAdminRequest request);
}
