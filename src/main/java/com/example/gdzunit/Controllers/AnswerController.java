package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Services.impl.AnswerServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/answers")
@Slf4j
public class AnswerController {

    private final AnswerServiceImpl answerService;
    private final UserServiceImpl userService;

    public AnswerController(AnswerServiceImpl answerService, UserServiceImpl userService) {
        this.answerService = answerService;
        this.userService = userService;
    }

    @GetMapping("/getanswers")
    public String showAnswerListBySubjectId(@RequestParam("subject") String subject, Model model){
        Short variantValue = userService.getCurrentUser().getVariant().getVariant_value();
        try {
            List<Answer> allAnswersBySubjectName = answerService.findAllAnswersBySubjectNameAndVariant(subject, variantValue);
            allAnswersBySubjectName.forEach((s) -> {
                log.info(s.toString());
            });
            model.addAttribute("answers", allAnswersBySubjectName);
        } catch (NoAnswersException e) {
            throw new RuntimeException(e);
        }
        return "answers";
    }

    @GetMapping("/showanswer")
    public String showAnswerById(@RequestParam("title") String title, Model model){




        try {
            Answer answerByAnswerTitle = answerService.findAnswerByAnswerTitle(title);
            model.addAttribute("answer", answerByAnswerTitle);
        } catch (NoAnswersException e) {
            log.info("Юзер попытался получить несуществующий ответ: " + title + " Время: " + new Date());
            return "redirect:/error404";
        }
        return "showAnswer";
    }

}
