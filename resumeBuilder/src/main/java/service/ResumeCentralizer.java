package service;

import model.Education;
import model.Experience;
import model.Project;
import model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EducationRepository;
import repository.ExperienceRepository;
import repository.ProjectRepository;
import repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeCentralizer {

    private ExperienceRepository experienceRepository;
    private SkillRepository skillRepository;
    private EducationRepository educationRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ResumeCentralizer(ExperienceRepository experienceRepository, SkillRepository skillRepository, EducationRepository educationRepository, ProjectRepository projectRepository) {
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }
    private boolean descriptionFilter(List<String> desc ,List<String> jobKeyWords) {
        for(String descriptionString: desc) {
            for(String keyword: jobKeyWords ) {
                if( descriptionString.toLowerCase().contains(keyword.toLowerCase())) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean tagFilter(List<String> tags ,List<String> jobKeyWords) {
        for(String tag: tags) {
            for(String keyword: jobKeyWords ) {
                if(tag.toLowerCase().contains(keyword.toLowerCase()) || keyword.toLowerCase().contains(tag.toLowerCase()))
                return true;
            }
        }
        return false;
    }

    private String listFormatter(List<String> descriptions) {
        StringBuilder res = new StringBuilder();
        for(String desc: descriptions) {
            res.append("<li>").append(desc).append("</li>");
        }
        return "<ul>"+res.toString()+"</ul>";
    }

    private String commaFormatter(List<String> items) {
        StringBuilder sb = new StringBuilder();
        items.forEach( item -> {
            sb.append(item).append(", ");
        });
        return sb.substring(0, sb.toString().length() - 2);
    }


//    <p>
//        <strong>Big Data Project Support Intern, Audi, China</strong> (03/2021 - 08/2021)
//    </p>
//    <ul>
//        <li>Built ELT pipelines to fetch real-time car condition data coming from the IoT hub and data warehouse</li>
//        <li>Used PySpark and Spark to create summary tables to monitor time series of car condition data</li>
//        <li>Employed logistic regression to predict whether a car would send an alarm and achieved 0.92 F1-score</li>
//    </ul>

    public String experienceFilter(List<String> jobKeyWord) {
        Iterable<Experience> experiences = experienceRepository.findAll();
        StringBuilder res = new StringBuilder();
        res.append("<h2>EXPERIENCE</h2>");
        for(Experience e: experiences) {
            if(descriptionFilter(e.getDescription(), jobKeyWord)) {
                String firstLine = String.format("<p><strong>%s, %s, %s</strong>(%s-%s)</p>",
                                                e.getTitle(), e.getCompany(), e.getCountry(), e.getStartDate().toString().substring(0, 7), e.getEndDate().toString().substring(0, 7)
                );

                res.append(firstLine).append(listFormatter(e.getDescription()));
            }
        }
        return res.toString();
    }


    public String projectFilter( List<String> jobKeyWord) {
        Iterable<Project> projects = projectRepository.findAll();
        StringBuilder res = new StringBuilder();
        res.append("<h2>PROJECTS</h2>");
        for(Project p: projects) {
            if(descriptionFilter(p.getDescription(), jobKeyWord) || tagFilter(p.getTags(), jobKeyWord)) {
                String firstLine = String.format("<p>\n" +
                        "    <strong>%s %s</strong> (%s - %s)\n" +
                        "  </p>", p.getName(), "( "+ (p.getTags()) +")",p.getStartDate().toString().substring(0,7), p.getEndDate().toString().substring(0, 7));
                res.append(firstLine).append(listFormatter(p.getDescription()));
            }

        }
        return res.toString();
    }
//<p>
//    <strong>MSc in Statistics, UBC, Canada</strong> (09/2021 - 05/2024, Expected)</p>
//

    public String educationFilter() {
        Iterable<Education> educations = educationRepository.findAll();
        StringBuilder res = new StringBuilder();
        res.append("<h2>EDUCATION</h2>");
        for(Education e : educations) {
            res.append(String.format("<p><strong>%s in %s, %s</strong> (%s - %s)</p>", e.getDegree(), e.getMajor(), e.getSchool(), e.getStartDate().toString().substring(0, 7), e.getEndDate().toString().substring(0, 7)));
        }
        return res.toString();
    }


    public String skillFilter() {
        Iterable<Skill> skills = skillRepository.findAll();
        StringBuilder res = new StringBuilder();
        res.append("<h2>TECHNOLOGICAL SKILLS</h2>");
        for(Skill s: skills) {
            res.append("<p>");
            res.append(String.format("<strong>%s :</strong>", s.getSkillName()));
            res.append(String.join(", ", s.getTags()));
            res.append("</p>");
        }

        return res.toString();
    }

    public String renderHTML(List<String> descriptions) {
        String frontPart = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>Yicheng (Eason) Wang - Resume</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      margin: 20px;\n" +
                "      line-height: 1.6;\n" +
                "    }\n" +
                "    h1 {\n" +
                "      font-size: 28px;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "    h2 {\n" +
                "      font-size: 22px;\n" +
                "      margin-bottom: 5px;\n" +
                "    }\n" +
                "    p {\n" +
                "      margin-top: 5px;\n" +
                "    }\n" +
                "    ul {\n" +
                "      padding-left: 20px;\n" +
                "      margin-top: 5px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Yicheng (Eason) Wang</h1>\n" +
                "  <p>yicheng.wang@stat.ubc.ca | +1 2369896133 | 2205 Lower Mall, Vancouver, BC V6T 1Z4</p>\n" +
                "  ";
        StringBuilder res = new StringBuilder();
        res.append(frontPart)
                .append(skillFilter())
                .append(educationFilter())
                .append(experienceFilter(descriptions))
                .append(projectFilter(descriptions));
        String endPart = "</body>\n" +
                "</html>";
        res.append(endPart);
        return res.toString();

    }
}
