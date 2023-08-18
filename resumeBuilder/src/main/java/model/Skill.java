package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Skill {

    @Id
    @Column(name = "skillName")
    private String skillName;
    @Column(name = "tags")
    private List<String> tags;

    public String getSkillName() {
        return this.skillName;
    }
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public List<String> getTags() {
        return this.tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }



}
