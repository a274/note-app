package com.khlopovskaya.noteapp.repository;

import com.khlopovskaya.noteapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<Note, String> {
    Long deleteById(int id);

    Note getById(int noteId);
}