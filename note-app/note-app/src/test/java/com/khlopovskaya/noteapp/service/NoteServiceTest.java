package com.khlopovskaya.noteapp.service;

import com.khlopovskaya.noteapp.model.Note;
import com.khlopovskaya.noteapp.model.NoteRequest;
import com.khlopovskaya.noteapp.repository.NoteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepo noteRepo;

    @Captor
    private ArgumentCaptor<Note> captor;

    private Note note1, note2, note3;

    @BeforeEach
    void setUp() {
        note1 = new Note();
        note1.setNote("test1");
        note1.setCreateTs(new Date());
        note1.setUserId(1);
        note1.setId(1);

        note2 = new Note();
        note2.setNote("test2");
        note2.setCreateTs(new Date());
        note2.setUserId(2);
        note2.setId(2);

        note3 = new Note();
        note3.setNote("test3");
        note3.setCreateTs(new Date());
        note3.setUserId(1);
        note3.setId(3);
    }

    @Test
    void getAll() {
        Mockito.when(noteRepo.findAll()).thenReturn(List.of(note1, note2, note3));
        assertEquals(List.of(note1, note2, note3), noteRepo.findAll());
    }

    @Test
    void saveNote() {
        noteService.saveNote(1, new NoteRequest("note test"));
        Mockito.verify(noteRepo).save(captor.capture());
        Note captured = captor.getValue();
        assertEquals("note test", captured.getNote());
    }

    @Nested
    class EditNoteTest {

        @Test
        void editNote() {
            Mockito.when(noteRepo.getById(1)).thenReturn(note1);
            noteService.editNote(1, 1, new NoteRequest("note test"));
            Mockito.verify(noteRepo).save(captor.capture());
            Note captured = captor.getValue();
            assertEquals("note test", captured.getNote());
        }

        @Test
        void shouldThrowException() {
            Mockito.when(noteRepo.getById(1)).thenReturn(note1);
            Exception exception = assertThrows(IllegalArgumentException.class, () -> noteService.editNote(2, 1, new NoteRequest("note test")));

            assertEquals("This user does not have such a note.", exception.getMessage());
        }

    }

}