package com.example.gdzunit.Handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class MaxFileUploadSizeExceededExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxFileUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return "redirect:/me?fileSizeError";
    }
}