package todo_list_app.todo_list_app.utils.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private List<T> items;
    private Integer totalItems;
    private Integer currentPage;
    private Integer totalPage;

    public PageResponse(Page<T> page){
        this.items = page.getContent();
        this.totalItems = (int) page.getTotalElements();
        this.currentPage = page.getNumber();
        this.totalPage = page.getTotalPages();
    }
}
