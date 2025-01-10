package it.network.note.service;

import it.network.note.data.entities.NoteEntity;
import it.network.note.data.entities.UserEntity;
import it.network.note.dto.NoteDTO;

import java.util.List;

public interface NoteService  {





    void saveNewNote(NoteDTO note);

    List<NoteDTO> getAll();

    NoteDTO getById(Long noteId);

    void edit(NoteDTO note);

    NoteEntity getNoteOrThrow(Long noteId);

    void delete(Long noteId);

    List<NoteDTO> getNotesByUserId(Long userId);

    List<NoteDTO> getNotesByUsername(String username);
}
