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
        UUID uuid=toUUID(id);
        if(uuid!=null)
        {
            return usersService.getById(uuid);
        }
        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addUserWithView(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "role", required = false) String role) {
        Role r=null;
            if(role!=null &&  EnumUtils.isValidEnum(Role.class, role.trim()))
            {
                r=Role.valueOf(role.trim());
            }
            else {
                r=null;
            }
            usersService.create(name.trim(), surname.trim(), r);
        return new ModelAndView("redirect:/stakeholders/");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateUserWithView(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "role", required = false) String role) {
        UUID uuid=toUUID(id);
        if(uuid!=null)
        {
            Users user=usersService.getById(uuid);
            if(user!=null)
            {
                user.setName(name.trim());
                user.setSurname(surname.trim());
                if(role!=null &&  EnumUtils.isValidEnum(Role.class, role.trim()))
                {
                    user.setRole(Role.valueOf(role.trim()));
                }
                usersService.update(user);
            }
        }
        return new ModelAndView("redirect:/stakeholders/");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUserByIdWithView(@PathVariable(value = "id") String id) {
        UUID uuid=toUUID(id);
        if(uuid!=null)
        {
            Users user=usersService.getById(uuid);
            if(user!=null)
            {
                usersService.delete(uuid);
            }
        }
        return new ModelAndView("redirect:/stakeholders/");
    }
    private final static Pattern UUID_REGEX_PATTERN = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
    public static UUID toUUID(String temp) {
        if(temp!=null && temp.length()==32)
        {
            temp=temp.substring(0,8)+"-"+temp.substring(8,12)+"-"+temp.substring(12,16)+"-"+temp.substring(16,20)+"-"+temp.substring(20);
            if(UUID_REGEX_PATTERN.matcher(temp).matches())
            {
                return UUID.fromString(temp);
            }
        }
        return null;
    }
}
