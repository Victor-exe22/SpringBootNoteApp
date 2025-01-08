package it.network.note.service;

import it.network.note.data.entities.NoteEntity;
import it.network.note.data.entities.UserEntity;
import it.network.note.data.repositories.NoteRepository;
import it.network.note.data.repositories.UserRepository;
import it.network.note.dto.NoteDTO;
import it.network.note.dto.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public void saveNewNote(NoteDTO note) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        UserEntity user = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NoteEntity noteEntity = new NoteEntity();

        noteEntity.setDescription(note.getDescription());
        noteEntity.setUser(user);
        noteEntity.setTitle(note.getTitle());

        noteRepository.save(noteEntity);
    }

    @Override
    public List<NoteDTO> getAll() {
        return StreamSupport.stream(noteRepository.findAll().spliterator(), false)
                .map(j -> noteMapper.toDTO(j))
                .toList();
    }
    @Override
    public NoteDTO getById(Long noteId) {
        NoteEntity fetchedNote = getNoteOrThrow(noteId);
        return noteMapper.toDTO(fetchedNote);
    }

    @Override
    public void edit(NoteDTO note) {
        NoteEntity fetchedEntity = getNoteOrThrow(note.getNoteId());
        noteMapper.updateNoteEntity(note, fetchedEntity);
        noteRepository.save(fetchedEntity);
    }

    @Override
    public NoteEntity getNoteOrThrow(Long noteId) {
        return noteRepository
                .findById(noteId)
                .orElseThrow();
    }

    @Override
    public void delete(Long noteId) {
        NoteEntity fetchedEntity = getNoteOrThrow(noteId);
        noteRepository.delete(fetchedEntity);
    }

    @Override
    public List<NoteDTO> getNotesByUserId(Long userId) {
        List<NoteEntity> noteEntities = noteRepository.findByUser_UserId(userId);

        return noteEntities.stream()
                .map(noteMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<NoteDTO> getNotesByUsername(String username) {
        List<NoteEntity> notes = noteRepository.findByUserEmail(username);

        return notes.stream()
                .map(note -> new NoteDTO(note.getNoteId(), note.getTitle(), note.getDescription()))
                .collect(Collectors.toList());
    }






}
