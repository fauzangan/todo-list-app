package todo_list_app.todo_list_app.exception;

public class CustomBadRequestException extends RuntimeException{
    public CustomBadRequestException(String message){
        super(message);
    }

    public CustomBadRequestException(String message, Throwable cause){
        super(message, cause);
    }
}
