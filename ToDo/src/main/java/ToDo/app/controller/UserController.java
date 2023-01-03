package ToDo.app.controller;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/stakeholders")
public class UserController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getAllUsersWithView() {
        ModelAndView view = new ModelAndView("stakeholders.html");
        view.addObject("users", usersService.getAll());
        return view;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Users> getAllUsers() {
        return usersService.getAll();
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public Users getUserById(@PathVariable(value = "id") String id) {
        return usersService.getById(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUserByIdWithView(@PathVariable(value = "id") String id) {
        usersService.delete(id);
        return new ModelAndView("redirect:/stakeholders/");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addUserWithView(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "role", required = false) String role) {
        usersService.create(name.trim(), surname.trim(), role);
        return new ModelAndView("redirect:/stakeholders/");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateUserWithView(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "role", required = false) String role) {
        usersService.update(id, name, surname, role);
        return new ModelAndView("redirect:/stakeholders/");
    }
    
}
