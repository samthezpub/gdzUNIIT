package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Services.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
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
    private final RoleServiceImpl roleService;

    public AnswerController(AnswerServiceImpl answerService, UserServiceImpl userService, SubjectServiceImpl subjectService, VariantServiceImpl variantService, RoleServiceImpl roleService) {
        this.answerService = answerService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.variantService = variantService;
        this.roleService = roleService;
    }

    @GetMapping("/getanswers")
    public String showAnswerListBySubjectId(@RequestParam("subject") String subject, Model model) {
        User currentUser = userService.getCurrentUser();
        Short variantValue =
                currentUser.getVariant().getVariant_value();

        Role adminRole = roleService.getAdminRole();

        model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));

        log.debug(variantValue.toString());
        try {
            List<Answer> allAnswersBySubjectName = answerService.findAllAnswers(subject, variantValue);
            model.addAttribute("answers", allAnswersBySubjectName);
        } catch (NoAnswersException e) {
            model.addAttribute("error", e.getMessage());
        }
        finally {
            model.addAttribute("user", currentUser);
        }
        return "answers";
    }

    // Возвращает представление addAnswer, с List<Subject>, List<Variant>
    @GetMapping("/addanswer")
    public String getAddAnswer(Model model) {
        model.addAttribute("variants", variantService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);

        Role adminRole = roleService.getAdminRole();
        model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));

        model.addAttribute("answer", new Answer());

        return "addAnswer";
    }

    // Обрабатывает полученный запрос и обрабатывает его,
    // добавляет полученный Answer в бд к определённому subject
    @PostMapping("/addanswer")
    public String addAnswer(@ModelAttribute("answer") Answer answer, Model model) {
        System.out.println(answer);
        answerService.addAnswer(answer);

        answer.setHtml(markdownToHTML(answer.getContent()));
        model.addAttribute("answer", answer);
        answerService.updateAnswer(answer);

        return "redirect:/answers/addanswer";
    }

    @GetMapping("/showanswer")
    public String showAnswerById(@RequestParam("title") String title, Model model) {
        try {
            Answer answerByAnswerTitle = answerService.findAnswerByAnswerTitle(title);
            User currentUser = userService.getCurrentUser();

            // Если вариант юзера равен варианту ответа или ответ для всех вариантов
            if (currentUser.getVariant().equals(answerByAnswerTitle.getVariant()) || answerByAnswerTitle.getIsForAllVariants()){
                model.addAttribute("user", currentUser);
                Role adminRole = roleService.getAdminRole();
                model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));

                model.addAttribute("answer", answerByAnswerTitle);
                model.addAttribute("user", currentUser);
                return "showAnswer";
            }
            else {
                return "403";
            }
        } catch (NoAnswersException e) {
            log.info("Юзер попытался получить несуществующий ответ: " + title + " Время: " + new Date());
            return "redirect:/error404";
        }

    }

    private String markdownToHTML(String markdown) {
        Parser parser = Parser.builder()
                .build();

        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .build();

        return renderer.render(document);
    }
}
