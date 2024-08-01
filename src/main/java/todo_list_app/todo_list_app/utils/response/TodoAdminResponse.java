package todo_list_app.todo_list_app.utils.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TodoAdminResponse {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String createdAt;
}
