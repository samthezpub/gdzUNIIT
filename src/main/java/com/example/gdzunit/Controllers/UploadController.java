package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Services.UserService;
import com.example.gdzunit.Services.impl.SubjectServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
public class UploadController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SubjectServiceImpl subjectService;

    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";
    private static String UPLOAD_DIRECTORY_AVATARS = UPLOAD_DIRECTORY + "\\avatars";
    private static String UPLOAD_DIRECTORY_SUBJECTS = UPLOAD_DIRECTORY + "\\subjects";

    public UploadController() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY_AVATARS));
        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY_SUBJECTS));
    }


    @PostMapping("/uploadAvatar")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();

        User currentUser = userService.getCurrentUser();
        String newFileName = currentUser.getUsername() + ".jpg";
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_AVATARS, newFileName);


        fileNames.append(newFileName);
        Files.write(fileNameAndPath, file.getBytes());

        // Устанавливаем аватар юзеру и обновляем
        currentUser.setAvatarURL(newFileName);
        userService.updateUser(currentUser);

        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());

        return "redirect:/me?success";
    }

    @PostMapping("/uploadSubject")
    public String uploadSubjectImage(@ModelAttribute Subject subject, Model model, @RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        StringBuilder fileNames = new StringBuilder();

        String newFileName = subject.getName() + ".jpg";
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_SUBJECTS, newFileName);

        fileNames.append(newFileName);
        Files.write(fileNameAndPath, file.getBytes());

        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        subject.setImage_url(newFileName);
        String referer = request.getHeader("Referer");
        System.out.println(subject);

        subjectService.addSubject(subject);

        return "redirect:" + referer;
    }
}
