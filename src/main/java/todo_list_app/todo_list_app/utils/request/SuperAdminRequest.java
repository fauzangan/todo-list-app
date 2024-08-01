package todo_list_app.todo_list_app.utils.request;

import lombok.Data;

@Data
public class SuperAdminRequest {
    private String username;
    private String email;
    private String password;
}
