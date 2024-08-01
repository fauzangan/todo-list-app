package todo_list_app.todo_list_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo_list_app.todo_list_app.model.ToDo;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {
    Page<ToDo> findByUserId(Integer userId, Pageable pageable);
}
