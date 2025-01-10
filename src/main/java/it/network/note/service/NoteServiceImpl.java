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
    public void delete(Long noteId) {

        noteRepository.deleteById(noteId);
    }

    @Override
    public NoteDTO getById(Long noteId) {
        NoteEntity fetchedNote = getNoteOrThrow(noteId);
        return noteMapper.toDTO(fetchedNote);
    }

    @Override
    public NoteEntity getNoteOrThrow(Long noteId) {
        return noteRepository
                .findById(noteId)
                .orElseThrow();
    }

    @Override
    public List<NoteDTO> getNotesByUserId(Long userId) {
        List<NoteEntity> noteEntities = noteRepository.findByUser_UserId(userId);

        return noteEntities.stream()
                .map(noteMapper::toDTO)
                .collect(Collectors.toList());

    }
}
