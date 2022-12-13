package ToDo.app.controller;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.repository.UsersRepository;
import ToDo.app.service.UsersService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void getAllUsersTest() throws Exception {
        List<Users> usersList = new ArrayList<>();
        usersList.add(createUsers("Matteo", "Severgnini", Role.BackEndDeveloper));
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));

        assertTrue(usersRepository.findAll().size() == 2);

        mockMvc.perform(get("/users/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-users"));
    }

    private Users createUsers(String name, String surname, Role role){
        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(role);
        usersRepository.save(users);
        return users;
    }

}
