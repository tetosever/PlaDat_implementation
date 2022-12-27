package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class DirectoryService {

    @Autowired
    private DirectoryRepository directoryRepository;

    public List<Directory> getAll(){
        return directoryRepository.findAll();
    }

    public Directory getByName(@RequestParam(value = "directory_id")String name){
        return directoryExists(name);
    }

    public Directory create(Directory directory){
        return directoryRepository.save(directory);
    }

    public void update(Directory directory){
        Directory savedDirectory = directoryExists(directory.getName());
        savedDirectory.setDirectory(directory.getDirectory());
        directoryRepository.save(savedDirectory);
    }

    public void delete(Directory directory){
        directoryRepository.delete(directoryExists(directory.getName()));
    }

    private Directory directoryExists(String name){
        Optional<Directory> optionalDirectory = directoryRepository.findByName(name);
        if (!optionalDirectory.isPresent()){
            // TODO: 27/12/22 eccezione personalizzata
        }
        return optionalDirectory.get();
    }
}
