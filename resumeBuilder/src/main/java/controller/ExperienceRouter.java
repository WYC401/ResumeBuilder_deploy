package controller;

import model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import repository.ExperienceRepository;

import java.util.Optional;

@Controller
public class ExperienceRouter {

    private final ExperienceRepository experienceRepository;
    @Autowired
    public ExperienceRouter(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }
    @PostMapping("/experience")
    public ResponseEntity<String> addExperience(@RequestBody Experience experience) {
        experienceRepository.save(experience);
        return new ResponseEntity<String>("saved", HttpStatus.CREATED);
    }

    @GetMapping("/experience")
    public ResponseEntity<Iterable<Experience>> getAll() {
        return new ResponseEntity<Iterable<Experience>>(experienceRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/experience")
    public ResponseEntity<String> deleteExperience(@RequestParam long id) {
        if(experienceRepository.findById(id).isPresent()) {
            experienceRepository.deleteById(id);
            return new ResponseEntity<String>("deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not Found",HttpStatus.OK);
        }
    }

    //TODO: why can we not just save the new experience from the requestbody to overwrite the old one since they have same id
    @PutMapping("/experience")
    public ResponseEntity<String> modifyExperience(@RequestBody Experience experience) {
        Optional<Experience> result = experienceRepository.findById(experience.getId());
        if(result.isPresent()) {
            Experience experienceOld = result.get();
            experienceOld.setCompany(experience.getCompany());
            experienceOld.setCountry(experience.getCountry());
            experienceOld.setDescription(experience.getDescription());
            experienceOld.setEndDate(experience.getEndDate());
            experienceOld.setStartDate(experience.getStartDate());
            experienceOld.setTitle(experience.getTitle());
            experienceRepository.save(experienceOld);
            return new ResponseEntity<String>("modified", HttpStatus.OK);
        } else {
            experienceRepository.save(experience);
            return new ResponseEntity<String>("saved", HttpStatus.CREATED);
        }
    }


}
