package ToDo.app.exception;

public class ToDoApplicationExceptionNotFound extends ToDoApplicationException {

    public ToDoApplicationExceptionNotFound(final String message)
    {
        super(message);
    }

    public ToDoApplicationExceptionNotFound(final Throwable cause)
    {
        super(cause);
    }

    public ToDoApplicationExceptionNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }
}
