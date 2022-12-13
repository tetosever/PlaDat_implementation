package ToDo.app.service;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.repository.UsersRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users createUsers(String name, String surname, Role role){
        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(role);
        return usersRepository.save(users);
    }

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

}
