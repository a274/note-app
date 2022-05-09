package com.khlopovskaya.noteapp.controller;

import com.khlopovskaya.noteapp.model.User;
import com.khlopovskaya.noteapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign")
    public RedirectView sign() {
        return new RedirectView("/");
    }

    @PostMapping("/sign")
    public RedirectView signUp(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) token.getPrincipal();
        if (userService.loadUserByUsername(user.getLogin()) == null) {
            userService.create(user);
        }
        authWithHttpServletRequest(request, user.getLogin(), user.getPassword());
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException ignored) { }
    }
}
