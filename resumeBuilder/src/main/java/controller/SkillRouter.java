package controller;

import model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: why app knows what controller does
@Controller
@RequestMapping(path="/skill")
public class SkillRouter {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillRouter(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @PostMapping(path="/")
    public ResponseEntity<String> addSkill (@RequestBody Skill skill) {

        skillRepository.save(skill);
        return new ResponseEntity<String>("saved", HttpStatus.CREATED);
    }
    @DeleteMapping("/")
    public void deleteSkill(@RequestParam String skillName) {
        skillRepository.deleteById(skillName);
    }

    @PutMapping("/")
    public ResponseEntity<String> modifySkill(@RequestBody Skill skill) {
        Optional<Skill> result = skillRepository.findById(skill.getSkillName());
        if(result.isPresent()) {
            Skill oldSkill = result.get();
            oldSkill.setTags(skill.getTags());
            skillRepository.save(oldSkill);
            return new ResponseEntity<String>("modified", HttpStatus.OK);
        } else {
            skillRepository.save(skill);
            return new ResponseEntity<String>("saved", HttpStatus.CREATED);
        }
    }
    @GetMapping(path="/")
    public ResponseEntity<Iterable<Skill>>  getAllSkills() {
        // This returns a JSON or XML with the users
        return new ResponseEntity<Iterable<Skill>>(skillRepository.findAll(), HttpStatus.OK);
    }


}