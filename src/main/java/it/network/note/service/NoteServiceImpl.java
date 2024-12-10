package it.network.note.service;

import it.network.note.data.entities.NoteEntity;
import it.network.note.data.repositories.NoteRepository;
import it.network.note.dto.NoteDTO;
import it.network.note.dto.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NoteServiceImpl {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private NoteRepository noteRepository;


    public void create(NoteDTO noteDTO) {
        NoteEntity newNote = noteMapper.toEntity(noteDTO);
        noteRepository.save(newNote);
    }

    public void addNewNote(NoteDTO noteDTO) {

    }


    public List<NoteDTO> getAll() {
        return StreamSupport.stream(noteRepository.findAll().spliterator(), false)
                .map(j -> noteMapper.toDTO(j))
                .toList();
    }

    public NoteDTO getById(Long noteId) {
        NoteEntity fetchedNote = getNoteOrThrow(noteId);
        return noteMapper.toDTO(fetchedNote);
    }


    public void edit(NoteDTO note) {
        NoteEntity fetchedEntity = getNoteOrThrow(note.getNoteId());
        noteMapper.updateNoteEntity(note, fetchedEntity);
        noteRepository.save(fetchedEntity);
    }

    private NoteEntity getNoteOrThrow(Long noteId) {
        return noteRepository
                .findById(noteId)
                .orElseThrow();
    }


    public void remove(Long noteId) {
        NoteEntity fetchedEntity = getNoteOrThrow(noteId);
        noteRepository.delete(fetchedEntity);
    }


}
