package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Services.impl.AnswerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/answers")
@Slf4j
public class AnswerController {

    private final AnswerServiceImpl answerService;

    public AnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/getanswers")
    public String showAnswersBySubjectId(@RequestParam("subject") String subject, Model model){
        try {
            List<Answer> allAnswersBySubjectName = answerService.findAllAnswersBySubjectName(subject);
            allAnswersBySubjectName.forEach((s) -> {
                log.info(s.toString());
            });
            model.addAttribute("answers", allAnswersBySubjectName);
        } catch (NoAnswersException e) {
            throw new RuntimeException(e);
        }
        return "answers";
    }


}
