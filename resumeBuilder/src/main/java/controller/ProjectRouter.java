package controller;

import jakarta.annotation.PostConstruct;
import model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.ProjectRepository;

import java.util.Optional;


//{
//        "name": "Resume Builder Extension",
//        "startDate": "2023-03-12",
//        "endDate": "2023-05-30",
//        "description": ["Implemented a responsive chrome extension user interface using HTML and JavaScript", "Architected and implemented RESTful APIs for users’ experience, education and skill management", "Ensured data integrity and persistence using JPA and MySQL database, following Spring MVC pattern"],
//        "tags": ["Spring Boot", "Java", "JavaScript", "MySQL"]
//}
@Controller
public class ProjectRouter {
    private final ProjectRepository projectRepository;
    @Autowired
    public ProjectRouter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PostMapping("/project")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        projectRepository.save(project);
        return new ResponseEntity<String>("saved", HttpStatus.CREATED);

    }

    @PutMapping("/project")
    public ResponseEntity<String> modifyProject(@RequestBody Project project) {
        Optional<Project> result = projectRepository.findById(project.getId());
        if(result.isPresent()) {
            Project projectOld = result.get();
            projectOld.setStartDate(project.getStartDate());
            projectOld.setEndDate(project.getEndDate());
            projectOld.setName(project.getName());
            projectOld.setDescription(project.getDescription());
            projectOld.setTags(project.getTags());
            projectRepository.save(projectOld);
            return new ResponseEntity<String>("modified", HttpStatus.OK);
        } else {
            projectRepository.save(project);
            return new ResponseEntity<String>("saved", HttpStatus.CREATED);
        }
    }

    @GetMapping("/project")
    public ResponseEntity<Iterable<Project>> getAll() {
        return new ResponseEntity<Iterable<Project>>(projectRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/project")
    public ResponseEntity<String> deleteProject(@RequestParam long id) {
        if(projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
            return new ResponseEntity<String>("deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not found", HttpStatus.OK);
        }
    }
}
