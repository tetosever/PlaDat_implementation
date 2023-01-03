package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.validation.DirectoryValidator;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectoryService {

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private DirectoryRepository directoryRepository;
    @Autowired
    private DirectoryValidator directoryValidator;

    public List<Directory> getAll(){
        return directoryRepository.findAll();
    }

    public Directory getById(String id) {
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);

        return directoryExists(uuid);
    }

    public Directory create(String name){
        directoryValidator.validateName(name);

        Directory directory = new Directory();
        directory.setName(name);
        return directoryRepository.save(directory);
    }

    public void update(String id, String name){
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);
        directoryValidator.validateName(name);

        Directory savedDirectory = directoryExists(uuid);
        savedDirectory.setName(name);
        //savedDirectory.setDirectory(directory.getDirectory());
        directoryRepository.save(savedDirectory);
    }

    public void delete(String id){
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);

        directoryRepository.delete(directoryExists(uuid));
    }

    private Directory directoryExists(UUID id){
        Optional<Directory> optionalDirectory = directoryRepository.findById(id);
        if (!optionalDirectory.isPresent()){
            throw new ToDoApplicationException("Directory is not present in database");
        }
        return optionalDirectory.get();
    }

    private static UUID toUUID(String id) {
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                    + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                throw new ToDoApplicationException("UUID error");
            }
        }
        return UUID.fromString(id);
    }
}
