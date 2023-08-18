package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Education {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @Column
    private String school;

    @Column
    private String major;

    @Column
    private Double gpa;

    @Column
    private String degree;

    @Column(length = 2000)
    private List<String> description;

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<String> getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getGpa() {
        return gpa;
    }

    public String getMajor() {
        return major;
    }

    public String getSchool() {
        return school;
    }

    public String getDegree() {
        return degree;
    }

    public long getId() {
        return id;
    }
}
