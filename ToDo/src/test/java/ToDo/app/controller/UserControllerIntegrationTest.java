package ToDo.app.controller;

import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    
    private List<Users> usersList = new ArrayList<>();
    
    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void getAllUsersWithViewTest() throws Exception {
        usersList.add(createUsers("Matteo", "Severgnini", Role.BackEndDeveloper));
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));
        
        assertTrue(userRepository.findAll().size() == 2);

        mockMvc.perform(get("/stakeholders"))
                .andExpect(status().isOk())
                .andExpect(view().name("stakeholders.html"));
    }

    /*
    Fare attenzione perchè penso che le liste non possono essere tornate, ma va sempre tornata una pagina html. 
    Penso che i valori vadano obbligatoriamente inseriti all'interno di una visualizzazione
    
    @Test
    public void getAllUsersTest() throws Exception {
        usersList.add(createUsers("Matteo", "Severgnini", Role.BackEndDeveloper));
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));

        assertTrue(userRepository.findAll().size() == 2);

        mockMvc.perform(get("/stakeholders/read"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users").exists());
    }
    
     */

    @Test
    public void deleteUserByIdWithView() throws Exception {
        usersList.add(createUsers("Matteo", "Severgnini", Role.BackEndDeveloper));
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));

        assertTrue(userRepository.findAll().size() == 2);

        mockMvc.perform(get("/stakeholders/delete/" + usersList.get(0).getId().toString()))
                .andExpect(redirectedUrl("/stakeholders/"));

        assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void addUserWithView() throws Exception {
        
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));

        assertTrue(userRepository.findAll().size() == 1);

        mockMvc.perform(post("/stakeholders/create")
                        .param("name", "Matteo")
                        .param("surname", "Severgnini")
                        .param("role", Role.BackEndDeveloper.toString()))
                .andExpect(redirectedUrl("/stakeholders/"));

        assertTrue(userRepository.findAll().size() == 2);
    }

    @Test
    public void updateUserWithView() throws Exception {
        usersList.add(createUsers("Matteo", "Severgnini", Role.BackEndDeveloper));
        usersList.add(createUsers("Mattia", "Piazzalunga", Role.FrontEndDeveloper));

        assertTrue(userRepository.findAll().size() == 2);

        mockMvc.perform(post("/stakeholders/update/" + usersList.get(0).getId().toString())
                        .param("name", "Marco")
                        .param("surname", "Severgnini")
                        .param("role", Role.Projectmanager.toString()))
                .andExpect(redirectedUrl("/stakeholders/"));

        assertTrue(userRepository.findAll().size() == 2);
        Users users = userRepository.findByName("Marco").get(0);
        assertEquals("Name should be equals", users.getName(), "Marco");
        assertEquals("Surname should be equals", users.getSurname(), "Severgnini");
        assertEquals("Role should be equals", users.getRole(), Role.Projectmanager);
        
    }

    private Users createUsers(String name, String surname, Role role){
        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(role);
        return userRepository.save(users);
    }

}
