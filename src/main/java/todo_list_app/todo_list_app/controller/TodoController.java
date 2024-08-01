package todo_list_app.todo_list_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo_list_app.todo_list_app.model.ToDo;
import todo_list_app.todo_list_app.service.ToDoService;
import todo_list_app.todo_list_app.utils.dto.ToDoDTO;
import todo_list_app.todo_list_app.utils.request.StatusRequest;
import todo_list_app.todo_list_app.utils.response.PageResponse;
import todo_list_app.todo_list_app.utils.response.Response;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final ToDoService toDoService;

    @PostMapping
    public ToDoDTO createTodo(@RequestBody ToDoDTO request) {
        return toDoService.create(request);
    }

    @GetMapping
    public PageResponse<ToDoDTO> getAllTodo(Pageable pageable){
        return new PageResponse<>(toDoService.getByUserId(pageable));
    }

    @GetMapping("/{id}")
    public ToDoDTO getByTodoId(@PathVariable Integer id){
        return toDoService.getByTodoId(id);
    }

    @PatchMapping("/{id}")
    public ToDo updateTodo(@PathVariable Integer id, @RequestBody ToDoDTO request){
        return toDoService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public ToDoDTO updateStatus(@PathVariable Integer id, @RequestBody StatusRequest request){
        return toDoService.updateTodoItemStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        toDoService.delete(id);
    }
}
