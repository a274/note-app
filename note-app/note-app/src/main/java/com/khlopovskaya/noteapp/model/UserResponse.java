package com.khlopovskaya.noteapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private int id;
    private String login;
    private String password;
    private List<NoteResponse> noteList;
    private String cookie;

    public UserResponse(User user, String cookie) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.noteList = map(user.getNoteList());
        this.cookie = cookie;
    }

    private List<NoteResponse> map(List<Note> noteList) {
        ArrayList<NoteResponse> noteResponses = new ArrayList<>();
        for (Note note : noteList)
            noteResponses.add(new NoteResponse(note));
        return noteResponses;
    }
}
