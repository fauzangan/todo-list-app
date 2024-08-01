package todo_list_app.todo_list_app.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo_list_app.todo_list_app.model.ToDo;
import todo_list_app.todo_list_app.model.ToDoStatus;
import todo_list_app.todo_list_app.model.User;
import todo_list_app.todo_list_app.repository.ToDoRepository;
import todo_list_app.todo_list_app.security.JwtService;
import todo_list_app.todo_list_app.service.ToDoService;
import todo_list_app.todo_list_app.utils.dto.ToDoDTO;
import todo_list_app.todo_list_app.utils.helper.DateFormatter;
import todo_list_app.todo_list_app.utils.request.StatusRequest;

@Service
@RequiredArgsConstructor
public class TodoServiceImplement implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final JwtService jwtService;

    @Override
    public ToDoDTO create(ToDoDTO request) {
        ToDo newTodo = ToDo.builder()
                .title(request.getTitle())
                .status(ToDoStatus.PENDING)
                .dueDate(DateFormatter.convertStringToDate(request.getDueDate(), "yyyy-MM-dd"))
                .description(request.getDescription())
                .user(jwtService.getUserAuthenticated())
                .build();

        ToDo createdTodo = toDoRepository.save(newTodo);


        return ToDoDTO.builder()
                .id(String.valueOf(createdTodo.getId()))
                .title(createdTodo.getTitle())
                .description(createdTodo.getDescription())
                .dueDate(DateFormatter.convertDateToString(createdTodo.getDueDate(), "yyyy-MM-dd"))
                .status(String.valueOf(createdTodo.getStatus()))
                .createdAt(DateFormatter.convertDateToString(createdTodo.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }

    @Override
    public Page<ToDoDTO> getByUserId(Pageable pageable) {
        User user = jwtService.getUserAuthenticated();

        return toDoRepository.findByUserId(user.getId(), pageable)
                .map(toDo -> ToDoDTO.builder()
                        .id(String.valueOf(toDo.getId()))
                        .title(toDo.getTitle())
                        .description(toDo.getDescription())
                        .dueDate(DateFormatter.convertDateToString(toDo.getDueDate(), "yyyy-MM-dd"))
                        .status(String.valueOf(toDo.getStatus()))
                        .createdAt(DateFormatter.convertDateToString(toDo.getCreatedAt(), "yyyy-MM-dd"))
                        .build());
    }

    @Override
    public ToDoDTO getByTodoId(Integer todoId) {
        ToDo toDo = toDoRepository.findById(todoId).orElseThrow(
                () -> new RuntimeException("Todo Not Found")
        );

        return ToDoDTO.builder()
                .id(String.valueOf(toDo.getId()))
                .title(toDo.getTitle())
                .description(toDo.getDescription())
                .dueDate(DateFormatter.convertDateToString(toDo.getDueDate(), "yyyy-MM-dd"))
                .status(String.valueOf(toDo.getStatus()))
                .createdAt(DateFormatter.convertDateToString(toDo.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }

    @Override
    public ToDoDTO updateTodoItemStatus(Integer id, StatusRequest request) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow( () -> new RuntimeException("Todo not found") );
        toDo.setStatus(ToDoStatus.valueOf(request.getStatus()));
        toDo = toDoRepository.save(toDo);

        return ToDoDTO.builder()
                .id(String.valueOf(toDo.getId()))
                .title(toDo.getTitle())
                .description(toDo.getDescription())
                .dueDate(DateFormatter.convertDateToString(toDo.getDueDate(), "yyyy-MM-dd"))
                .status(String.valueOf(toDo.getStatus()))
                .createdAt(DateFormatter.convertDateToString(toDo.getCreatedAt(), "yyyy-MM-dd"))
                .build();
    }

    @Override
    public ToDo update(Integer todoID, ToDoDTO request) {
        ToDo oldTodo = toDoRepository.findById(todoID).orElseThrow(() -> new RuntimeException("Todo cannot be found"));

        oldTodo.setTitle(request.getTitle());
        oldTodo.setDescription(request.getDescription());
        oldTodo.setStatus(ToDoStatus.valueOf(request.getStatus()));
        oldTodo.setDueDate(DateFormatter.convertStringToDate(request.getDueDate(), "yyyy-MM-dd"));

        return toDoRepository.save(oldTodo);
    }

    @Override
    public void delete(Integer todoId) {
        toDoRepository.deleteById(todoId);
    }
}
