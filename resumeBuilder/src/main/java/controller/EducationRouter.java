package controller;

import model.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.EducationRepository;

import java.util.Optional;

@Controller
public class EducationRouter {
    private EducationRepository educationRepository;
    @Autowired
    public EducationRouter(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @GetMapping("/education")
    public ResponseEntity<Iterable<Education>> getAll() {
        return new ResponseEntity<Iterable<Education>>(educationRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/education")
    public ResponseEntity<String> addEducation(@RequestBody Education education) {
        educationRepository.save(education);
        return new ResponseEntity<String>("created", HttpStatus.CREATED);
    }

    @PutMapping("/education")
    public ResponseEntity<String> modifyEducation(@RequestBody Education education) {
        Optional<Education> result = educationRepository.findById(education.getId());
        if(result.isPresent()) {
            Education educationOld = result.get();
            educationOld.setStartDate(education.getStartDate());
            educationOld.setEndDate(education.getEndDate());
            educationOld.setDescription(education.getDescription());
            educationOld.setSchool(education.getSchool());
            educationOld.setMajor(education.getMajor());
            educationOld.setDegree(education.getDegree());
            educationOld.setGpa(education.getGpa());
            educationRepository.save(educationOld);
            return new ResponseEntity<String>("modified", HttpStatus.OK);

        } else {
            educationRepository.save(education);
            return new ResponseEntity<String>("saved", HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/education")
    public ResponseEntity<String> deleteExperience(@RequestParam long id) {
        if(educationRepository.findById(id).isPresent()) {
            educationRepository.deleteById(id);
            return new ResponseEntity<String>("deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not Found", HttpStatus.OK);
        }
    }
}
