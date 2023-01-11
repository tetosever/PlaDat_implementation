package ToDo.app.controller;

import ToDo.app.exception.ToDoApplicationExceptionNotFound;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ToDoApplicationErrorController implements ErrorController {

    @RequestMapping( "/error")
    public void getErrorPage() {
        // TODO: 11/01/23 fare controlli sullo status 
        throw new ToDoApplicationExceptionNotFound("Wrong route");
    }
}
