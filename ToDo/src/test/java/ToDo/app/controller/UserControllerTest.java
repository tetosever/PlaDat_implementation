package ToDo.app.controller;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.repository.UserRepository;
import ToDo.app.service.UsersService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllUsersTest() throws Exception {
        List<Users> usersList = new ArrayList<>();
        usersList.add(createUsers("Matteo", "Severgnini", Role.BackEndDeveloper));
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));

        assertTrue(userRepository.findAll().size() == 2);

        mockMvc.perform(get("/stakeholders"))
                .andExpect(status().isOk())
                .andExpect(view().name("stakeholders.html"));
    }

    private Users createUsers(String name, String surname, Role role){
        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(role);
        userRepository.save(users);
        return users;
    }

}
