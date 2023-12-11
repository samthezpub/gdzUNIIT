package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Exceptions.NoSuchSubjectException;
import com.example.gdzunit.Services.impl.AnswerServiceImpl;
import com.example.gdzunit.Services.impl.SubjectServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import com.example.gdzunit.Services.impl.VariantServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/answers")
@Slf4j
public class AnswerController {

    private final AnswerServiceImpl answerService;
    private final UserServiceImpl userService;
    private final SubjectServiceImpl subjectService;
    private final VariantServiceImpl variantService;

    public AnswerController(AnswerServiceImpl answerService, UserServiceImpl userService, SubjectServiceImpl subjectService, VariantServiceImpl variantService) {
        this.answerService = answerService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.variantService = variantService;
    }

    @GetMapping("/getanswers")
    public String showAnswerListBySubjectId(@RequestParam("subject") String subject, Model model){
        Short variantValue =
                userService.getCurrentUser().getVariant().getVariant_value();

        log.debug(variantValue.toString());
        try {
            List<Answer> allAnswersBySubjectName = answerService.findAllAnswersBySubjectNameAndVariant(subject, variantValue);
            model.addAttribute("answers", allAnswersBySubjectName);
        } catch (NoAnswersException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "answers";
    }

    // Возвращает представление addAnswer, с List<Subject>, List<Variant>
    @GetMapping("/addanswer")
    public String getAddAnswer(Model model){
        model.addAttribute("variants", variantService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "addAnswer";
    }

    // Обрабатывает полученный запрос и обрабатывает его,
    // добавляет полученный Answer в бд к определённому subject
    @PostMapping("/addanswer")
    public String addAnswer(@ModelAttribute("answer") Answer answer, Model model){
        System.out.println(answer);
        answerService.addAnswer(answer);
        return "redirect:/answers/addanswer";
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
