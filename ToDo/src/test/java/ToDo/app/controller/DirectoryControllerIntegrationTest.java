package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.repository.DirectoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DirectoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectoryRepository directoryRepository;

    @BeforeEach
    public void setup() {
        directoryRepository.deleteAll();
    }
    
    @Test
    public void getAllTaskWithViewTest() throws Exception {
        Directory directory = createDirectory("Università", 
            createDirectory("Esame1", null));
        
        assertTrue(directoryRepository.findAll().size() == 2);

        mockMvc.perform(get("/directories"))
            .andExpect(status().isOk())
            .andExpect(view().name("directories.html"));
    }

    @Test
    public void deleteTaskByIdWithViewTest() throws Exception {
        Directory directory = createDirectory("Università",
            createDirectory("Esame1", null));
        
        assertTrue(directoryRepository.findAll().size() == 2);

        mockMvc.perform(get("/directories/delete/" + directory.getId().toString()))
            .andExpect(redirectedUrl("/directories/"));

        assertTrue(directoryRepository.findAll().size() == 1);

    }

    @Test
    public void addTaskWithViewTest() throws Exception {
        Directory directory = createDirectory("Università",
            createDirectory("Esame1", null));
    
        assertTrue(directoryRepository.findAll().size() == 2);

        mockMvc.perform(post("/directories/create")
                .param("name", "Esame2"))
            .andExpect(redirectedUrl("/directories/"));

        assertTrue(directoryRepository.findAll().size() == 3);

    }

    @Test
    public void updateTaskWithViewTest() throws Exception {
        Directory directory = createDirectory("Università",
            createDirectory("Esame1", null));

        assertTrue(directoryRepository.findAll().size() == 2);

        //update directory named "Universitá" to "Esame2" and changed parent directory to null
        mockMvc.perform(post("/directories/update/" + directory.getId().toString())
                .param("name", "Esame2"))
            .andExpect(redirectedUrl("/directories/"));

        assertTrue(directoryRepository.findAll().size() == 2);
        assertEquals("Directory name should be equals", directoryRepository.findAll().get(1).getName(), "Esame2");
    }

    private Directory createDirectory(String name, Directory directory) {Directory directory_tmp = new Directory();
        directory_tmp.setName(name);
        directory_tmp.setDirectory(directory);
        return directoryRepository.save(directory_tmp);
    }
}
