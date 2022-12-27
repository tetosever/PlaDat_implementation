package ToDo.app.service;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.repository.UsersRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users create(String name, String surname, Role role){
        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(role);
        //in questo caso potrei passare direttamente un oggetto e non i vari parametri
        return usersRepository.save(users);
    }

    public List<Users> getAll(){
        return usersRepository.findAll();
    }

    public void delete(Long id){
        if(id == null) {
            // TODO: 25/12/22 mettere eccezione personalizzata in modo che si capisca
        }
        usersRepository.deleteById(id);
    }

    public Users getById(Long id){
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent()){
            // TODO: 27/12/22 eccezione personalizzata
        }
        return optionalUsers.get();
    }

    public List<Users> getByName(String name){
        List<Users> usersList = usersRepository.findByName(name);
        if (usersList == null || usersList.isEmpty()){
            // TODO: 27/12/22 eccezione personalizzata
        }
        return usersList;
    }

    public void update(Users updateUser){
        Optional<Users> optionalUsers = usersRepository.findById(updateUser.getId());
        if (!optionalUsers.isPresent()){
            // TODO: 27/12/22 eccezione personalizzata
        }
        Users savedUser = optionalUsers.get();
        savedUser.setName(updateUser.getName());
        savedUser.setSurname(updateUser.getSurname());
        savedUser.setRole(updateUser.getRole());
        usersRepository.save(savedUser);
    }
}
