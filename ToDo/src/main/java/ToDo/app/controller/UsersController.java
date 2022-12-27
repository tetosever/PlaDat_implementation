package ToDo.app.controller;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllUsers(){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("users", usersService.getAll());
        return view;
    }

    @RequestMapping(value = "/getUsersById", method = RequestMethod.GET)
    public ModelAndView getUsersById(
            @RequestParam(value = "id", required = false) Long id){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("user", usersService.getById(id));
        return view;
    }

    @RequestMapping(value = "/getUsersByName", method = RequestMethod.GET)
    public ModelAndView getUsersByName(
            @RequestParam(value = "name", required = false) String name){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("user", usersService.getByName(name));
        return view;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addUsers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname){
        ModelAndView view = new ModelAndView("view-all-users.html");
        usersService.create(name, surname, Role.FrontEndDeveloper);
        view.addObject("users", usersService.getAll());
        return view;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView updateUsers(Users users){
        ModelAndView view = new ModelAndView("view-all-users.html");
        usersService.update(users);
        view.addObject("users", usersService.getAll());
        return view;
    }

    //usato la post perchè con il mio piccolo front-end non andava delete
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteUser(
            @RequestParam(value = "id", required = false) Long id){
        ModelAndView view = new ModelAndView("view-all-users.html");
        usersService.delete(id);
        view.addObject("users", usersService.getAll());
        return view;
    }
}
