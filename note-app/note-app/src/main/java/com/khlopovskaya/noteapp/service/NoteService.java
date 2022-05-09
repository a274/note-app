package com.khlopovskaya.noteapp.service;

import com.khlopovskaya.noteapp.model.Note;
import com.khlopovskaya.noteapp.model.NoteRequest;
import com.khlopovskaya.noteapp.repository.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepo noteRepo;

    @Autowired
    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public List<Note> getAll() {
        return noteRepo.findAll();
    }

    public void saveNote(int id, NoteRequest noteRequest) {
        Note note = new Note();
        note.setUserId(id);
        note.setNote(noteRequest.getNote());
        note.setCreateTs(new Date());
        noteRepo.save(note);
    }

    public void editNote(int userId, int noteId, NoteRequest noteRequest) {
        Note note = noteRepo.getById(noteId);
        if (note.getUserId() == userId) {
            note.setNote(noteRequest.getNote());
            note.setCreateTs(new Date());
            noteRepo.save(note);
        } else throw new IllegalArgumentException("This user does not have such a note.");
    }

    @Transactional
    public void deleteNote(int id) {
        noteRepo.deleteById(id);
    }
}
