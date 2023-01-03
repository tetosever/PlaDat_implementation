package ToDo.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ToDoApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ToDoApplicationControllerAdvice.class);
    
    @ExceptionHandler(ToDoApplicationException.class)
    public ModelAndView handleToDoApplicationException(ToDoApplicationException ex) {
        logger.error("Error: ", ex);
        ModelAndView view = new ModelAndView("exception.html");
        view.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
        view.addObject("exception", ex.getClass());
        view.addObject("message", ex.getMessage());
        return view;
    }

    @ExceptionHandler(ToDoApplicationExceptionBadRequest.class)
    public ModelAndView handleToDoApplicationBadRequestException(ToDoApplicationExceptionBadRequest ex) {
        logger.error("Error: ", ex);
        ModelAndView view = new ModelAndView("exception.html");
        view.addObject("status", HttpStatus.BAD_REQUEST);
        view.addObject("exception", ex.getClass());
        view.addObject("message", ex.getMessage());
        return view;
    }

    @ExceptionHandler(ToDoApplicationExceptionNotFound.class)
    public ModelAndView handleToDoApplicationExceptionNotFound(ToDoApplicationExceptionNotFound ex) {
        logger.error("Error: ", ex);
        ModelAndView view = new ModelAndView("exception.html");
        view.addObject("status", HttpStatus.NOT_FOUND);
        view.addObject("exception", ex.getClass());
        view.addObject("message", ex.getMessage());
        return view;
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex) {
        logger.error("Error: ", ex);
        throw new ToDoApplicationException(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException ex) {
        logger.error("Error: ", ex);
        throw new ToDoApplicationException(ex.getMessage());
    }
}
