package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.validation.DirectoryValidator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private DirectoryRepository directoryRepository;
    
    @Autowired
    private DirectoryValidator directoryValidator;

    public List<Directory> getAllForUpdate(String id) {
        List<Directory> directoryList = directoryRepository.findAll();
        if (id != null) {
            for (Directory directory : directoryList) {
                UUID uuid = toUUID(id);
                if (checkParentDirectory(uuid, directory)) {
                    directoryList.remove(directory);
                }
            }
        }
        // TODO: 08/01/23 c'é la possibilitá di usare checkParentDirectory per tornare la lista solo delle giuste directory  
        return directoryList;
    }
    
    public List<Directory> getAll() {
        return directoryRepository.findAll();
    }
    
    public List<Directory> getAllParents(){
        //if directory attribute is null, the specific directory has not a parent. 
        return directoryRepository.findByDirectory(null);
    }

    public Directory getById(String id) {
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);

        return directoryExists(uuid);
    }
    
    public List<Directory> getAllDaughter(String id) {
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);

        Directory directory = directoryExists(uuid);
        return directoryRepository.findByDirectory(directory);
    }

    public Directory create(String name, String directory_id){
        directoryValidator.validateName(name);

        Directory directory = new Directory();
        directory.setName(name);
        if (directory_id != null && !directory_id.isEmpty()) {
            directory.setDirectory(directoryExists(toUUID(directory_id)));
        }
        return directoryRepository.save(directory);
    }

    public void update(String id, String name, String directory_id){
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);
        directoryValidator.validateName(name);

        Directory savedDirectory = directoryExists(uuid);
        
        if (directory_id == null || directory_id.isEmpty()) {
            savedDirectory.setName(name);
            savedDirectory.setDirectory(null);
        }
        else {
            Directory parentDirectory = directoryExists(toUUID(directory_id));
            if (checkParentDirectory(savedDirectory.getId(), parentDirectory)) {
                throw new ToDoApplicationExceptionBadRequest("New directory should create a loop association!!");
            }
            else
            {
                savedDirectory.setName(name);
                savedDirectory.setDirectory(parentDirectory);
            }
        }
        directoryRepository.save(savedDirectory);
    }

    @Transactional
    public void delete(String id){
        UUID uuid = toUUID(id);
        directoryValidator.validateId(uuid);

        directoryRepository.delete(directoryExists(uuid));
    }
    
    private boolean checkParentDirectory(final UUID targetId, Directory parent) {
        if (parent == null) {
            return false;
        }
        else if (parent.getId() != targetId) {
            return checkParentDirectory(targetId, parent.getDirectory());
        }
        else
        {
            return true;
        }
    }

    private Directory directoryExists(UUID id){
        Optional<Directory> optionalDirectory = directoryRepository.findById(id);
        if (!optionalDirectory.isPresent()){
            throw new ToDoApplicationException("Directory is not present in database");
        }
        return optionalDirectory.get();
    }

    private static UUID toUUID(String id) {
        UUID uuid = null;
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                throw new ToDoApplicationException("UUID error");
            }
            uuid = UUID.fromString(id);
        }
        else if (id.contains("-") && id.length() == 36) {
            uuid = UUID.fromString(id);
        }
        return uuid;
    }
}
