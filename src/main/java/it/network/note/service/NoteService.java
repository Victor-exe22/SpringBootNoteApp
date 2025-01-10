package it.network.note.service;

import it.network.note.data.entities.NoteEntity;
import it.network.note.data.entities.UserEntity;
import it.network.note.dto.NoteDTO;

import java.util.List;

public interface NoteService  {

    void saveNewNote(NoteDTO note);

    NoteDTO getById(Long noteId);

    NoteEntity getNoteOrThrow(Long noteId);

    void delete(Long noteId);

    List<NoteDTO> getNotesByUserId(Long userId);

}
