package com.khlopovskaya.noteapp.controller;

import com.khlopovskaya.noteapp.model.NoteRequest;
import com.khlopovskaya.noteapp.model.User;
import com.khlopovskaya.noteapp.model.UserResponse;
import com.khlopovskaya.noteapp.service.NoteService;
import com.khlopovskaya.noteapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getAllNotesByUser(HttpServletRequest request, Authentication authentication) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        UserResponse userResponse = new UserResponse(user, request.getHeader("Cookie"));
        return ResponseEntity.ok().body(new UserResponse(user, request.getHeader("Cookie")));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createNote(HttpServletRequest request, Authentication authentication, @RequestBody NoteRequest noteRequest) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        noteService.saveNote(user.getId(), noteRequest);
        user = userService.getUserById(user.getId());
        return ResponseEntity.ok().body(new UserResponse(user, request.getHeader("Cookie")));
    }

    @PutMapping
    public ResponseEntity<UserResponse> editNote(HttpServletRequest request, Authentication authentication,
                                                 @RequestParam(name = "id") int id,
                                                 @RequestBody NoteRequest noteRequest) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        noteService.editNote(user.getId(), id, noteRequest);
        user = userService.getUserById(user.getId());
        return ResponseEntity.ok().body(new UserResponse(user, request.getHeader("Cookie")));
    }

    @DeleteMapping
    public ResponseEntity<UserResponse> deleteNote(HttpServletRequest request, Authentication authentication, @RequestParam(name = "id") int id) {
        noteService.deleteNote(id);
        User user = (User) userService.loadUserByUsername(authentication.getName());
        return ResponseEntity.ok().body(new UserResponse(user, request.getHeader("Cookie")));
    }
}
