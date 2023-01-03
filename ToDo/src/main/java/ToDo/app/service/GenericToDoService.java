package ToDo.app.service;

import ToDo.app.domain.GenericToDo;
import ToDo.app.repository.GenericToDoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericToDoService {

    @Autowired
    private GenericToDoRepository genericToDoRepository;

    public List<GenericToDo> getAll() {
        return genericToDoRepository.findAll();
    }
}
