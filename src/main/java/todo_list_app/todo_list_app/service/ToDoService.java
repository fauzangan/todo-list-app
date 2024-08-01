package todo_list_app.todo_list_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import todo_list_app.todo_list_app.model.ToDo;
import todo_list_app.todo_list_app.utils.dto.ToDoDTO;
import todo_list_app.todo_list_app.utils.request.StatusRequest;

import java.util.List;

public interface ToDoService {
    ToDoDTO create(ToDoDTO request);
    Page<ToDoDTO> getByUserId(Pageable pageable);
    ToDoDTO getByTodoId(Integer todoId);
    ToDoDTO updateTodoItemStatus(Integer id, StatusRequest request);
    ToDo update(Integer todoId, ToDoDTO request);
    void delete(Integer taskId);
}
