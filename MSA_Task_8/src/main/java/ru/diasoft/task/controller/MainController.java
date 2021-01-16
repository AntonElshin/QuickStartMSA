package ru.diasoft.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    private static final String template = "Hello, %s!";

    @RequestMapping("/index")
    public String getMainPage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        return String.format(template, name);
    }

    @RequestMapping("/")
    public String getEmptyMainPage(Principal principal) {

        String name = principal.getName();
        return String.format(template, name);
    }

}
