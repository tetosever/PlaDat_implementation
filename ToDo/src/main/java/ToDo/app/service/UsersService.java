package ToDo.app.service;

import ToDo.app.domain.Event;
import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.UserRepository;
import ToDo.app.validation.UserValidator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.regex.Pattern;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    public Users getById(String id) {
        UUID uuid = toUUID(id);
        userValidator.validateId(uuid);

        return userExists(uuid);
    }

    public List<Users> getAll(){
        return userRepository.findAll();
    }

    public Users create(String name, String surname, String role){
        userValidator.validateUser(name, surname, role);

        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(Role.valueOf(role));
        return userRepository.save(users);
    }

    public void delete(String id){
        UUID uuid = toUUID(id);
        userValidator.validateId(uuid);

        Users users = userExists(uuid);
        userRepository.delete(users);
    }

    public List<Users> getByName(String name){
        return userRepository.findByName(name);
    }

    public void update(String id, String name, String surname, String role) {
        UUID uuid = toUUID(id);
        userValidator.validateId(uuid);
        userValidator.validateUser(name, surname, role);

        Users savedUser = userExists(uuid);
        savedUser.setName(name);
        savedUser.setSurname(surname);
        savedUser.setRole(Role.valueOf(role));
        userRepository.save(savedUser);
    }

    private Users userExists(UUID uuid){
        Optional<Users> optionalUser = userRepository.findById(uuid);
        if (!optionalUser.isPresent()) {
            throw new ToDoApplicationException("User is not present in database");
        }
        return optionalUser.get();
    }

    private static UUID toUUID(String id) {
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                    + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                // TODO: 02/01/23 fare eccezione personalizzata
            }
        }
        return UUID.fromString(id);
    }
}
