package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Answer;
import com.example.gdzunit.Entity.Comment;
import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Exceptions.NoAnswersException;
import com.example.gdzunit.Exceptions.NoCommentsException;
import com.example.gdzunit.Services.CommentService;
import com.example.gdzunit.Services.impl.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/answers")
@Slf4j
public class AnswerController {

    private final AnswerServiceImpl answerService;
    private final UserServiceImpl userService;
    private final SubjectServiceImpl subjectService;
    private final VariantServiceImpl variantService;
    private final RoleServiceImpl roleService;
    private final CommentServiceImpl commentService;

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
        } finally {
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
        if (currentUser.getRoles().contains(adminRole)) {
            model.addAttribute("isUserHaveAdminRole", true);
            model.addAttribute("answer", new Answer());
            return "addAnswer";
        } else {
            return "403";
        }
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

    @GetMapping("/showanswer/{id}")
    public String showAnswerById(@PathVariable Long id, Model model) {
        try {
            Answer answerByAnswerTitle = answerService.getAnswerById(id);
            User currentUser = userService.getCurrentUser();

            List<Comment> commentList = commentService.getAllCommentsByAnswerId(answerByAnswerTitle.getId());

            // Если вариант юзера равен варианту ответа или ответ для всех вариантов
            if (currentUser.getVariant().equals(answerByAnswerTitle.getVariant()) || answerByAnswerTitle.getIsForAllVariants()) {
                model.addAttribute("user", currentUser);
                Role adminRole = roleService.getAdminRole();
                model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));

                model.addAttribute("comments", commentList);

                if (commentList.isEmpty()){
                    model.addAttribute("noComments", true);
                }

                model.addAttribute("answer", answerByAnswerTitle);
                model.addAttribute("user", currentUser);
                return "showAnswer";
            }
        } catch (NoAnswersException e) {
            log.info("Юзер попытался получить несуществующий ответ: " + id + " Время: " + new Date());
            return "redirect:/error404";
        } catch (NoCommentsException e) {

        }
        return "403";
    }


    @PostMapping("/addcomment/{id}")
    public String addComment(@ModelAttribute("comment") Comment comment, @PathVariable Long id) {
        try {
            comment.setAnswer(answerService.getAnswerById(id));
            comment.setAuthor(userService.getCurrentUser());
            comment.setPublishDate(LocalDateTime.now());
            commentService.addComment(comment);
        } catch (NullPointerException e){

        } catch (NoAnswersException e) {

        }

        return null;
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
