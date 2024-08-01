package todo_list_app.todo_list_app.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import todo_list_app.todo_list_app.model.User;

@Component
public class UserSecurity {
    public boolean isUser(Authentication authentication, int userId){
        if (authentication == null || !authentication.isAuthenticated()){
            return false;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User)) {
            return false;
        }

        User user = (User) principal;

        return user.getId() == userId;
    }
}
