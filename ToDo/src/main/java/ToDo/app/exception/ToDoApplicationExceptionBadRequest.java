package ToDo.app.exception;

public class ToDoApplicationExceptionBadRequest extends ToDoApplicationException {

    public ToDoApplicationExceptionBadRequest(final String message)
    {
        super(message);
    }

    public ToDoApplicationExceptionBadRequest(final Throwable cause)
    {
        super(cause);
    }

    public ToDoApplicationExceptionBadRequest(final String message, final Throwable cause) {
        super(message, cause);
    }
}
