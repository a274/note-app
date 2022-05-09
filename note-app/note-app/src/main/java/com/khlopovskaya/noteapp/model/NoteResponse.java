package com.khlopovskaya.noteapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;

@Setter
@Getter
@AllArgsConstructor
public class NoteResponse {
    private int id;
    private String date;
    private String note;

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(note.getCreateTs());
        this.note = note.getNote();
    }
}
