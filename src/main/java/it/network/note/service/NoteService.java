package it.network.note.service;

import it.network.note.dto.NoteDTO;

import java.util.List;
import java.util.Locale;

public interface NoteService {

    void create(NoteDTO noteDTO);

    List<NoteDTO> getAll();

    NoteDTO getById(long noteId);

    void edit(NoteDTO noteDTO);

    void remove(long noteId);
}
