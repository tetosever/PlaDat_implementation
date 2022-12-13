package ToDo.app.controller;

import ToDo.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String getAllUsers(ModelMap model){
        model.put("users", usersService.getAllUsers());
        return "view-all-users";
    }
}
