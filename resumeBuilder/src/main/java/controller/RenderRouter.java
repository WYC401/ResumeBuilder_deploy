package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import service.ResumeCentralizer;

import java.util.Arrays;
import java.util.List;

@Controller
public class RenderRouter {
    private ResumeCentralizer rc;

    @Autowired
    public RenderRouter(ResumeCentralizer rc) {
        this.rc = rc;
    }

    @GetMapping("/resume")
    public ResponseEntity<String> render(@RequestParam String jobKeywords) {
        String[] kws =jobKeywords.replaceAll("\\s", "").split(",");
        return new ResponseEntity<String>(rc.renderHTML(Arrays.asList(kws)), HttpStatus.OK);
    }
}
