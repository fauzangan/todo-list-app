package todo_list_app.todo_list_app.utils.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDoDTO {
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String createdAt;
}
