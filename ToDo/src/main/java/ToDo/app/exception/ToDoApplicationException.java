package ToDo.app.exception;

public class ToDoApplicationException extends RuntimeException {

    public ToDoApplicationException(final String message)
    {
        super(message);
    }

    public ToDoApplicationException(final Throwable cause)
    {
        super(cause);
    }

    public ToDoApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
