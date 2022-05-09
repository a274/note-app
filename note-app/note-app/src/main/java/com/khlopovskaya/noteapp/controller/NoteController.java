package com.khlopovskaya.noteapp.controller;

import com.khlopovskaya.noteapp.model.NoteRequest;
import com.khlopovskaya.noteapp.model.User;
import com.khlopovskaya.noteapp.model.UserResponse;
import com.khlopovskaya.noteapp.service.NoteService;
import com.khlopovskaya.noteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserResponse> getAllNotesByUser(Authentication authentication) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        return ResponseEntity.ok().body(new UserResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createNote(Authentication authentication, @RequestBody NoteRequest noteRequest) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        noteService.saveNote(user.getId(), noteRequest);
        user = userService.getUserById(user.getId());
        return ResponseEntity.ok().body(new UserResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserResponse> editNote(Authentication authentication,
                                                 @RequestParam(name = "id") int id,
                                                 @RequestBody NoteRequest noteRequest) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        noteService.editNote(user.getId(), id, noteRequest);
        user = userService.getUserById(user.getId());
        return ResponseEntity.ok().body(new UserResponse(user));
    }

    @DeleteMapping
    public ResponseEntity<UserResponse> deleteNote(Authentication authentication, @RequestParam(name = "id") int id) {
        noteService.deleteNote(id);
        User user = (User) userService.loadUserByUsername(authentication.getName());
        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
