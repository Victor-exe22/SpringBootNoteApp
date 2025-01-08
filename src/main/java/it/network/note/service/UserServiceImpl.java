package it.network.note.service;

import it.network.note.data.entities.UserEntity;
import it.network.note.data.repositories.NoteRepository;
import it.network.note.data.repositories.UserRepository;
import it.network.note.dto.UserDTO;
import it.network.note.models.exceptions.DuplicateEmailException;
import it.network.note.models.exceptions.PasswordsDoNotEqualException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public void create(UserDTO user, boolean isAdmin) {
        if (!user.getPassword().equals(user.getConfirmPassword()))
            throw new PasswordsDoNotEqualException();

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setUsername(user.getUsername());
        userEntity.setAdmin(isAdmin);

        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }

    @Override
    public boolean validateLogin(String email, String rawPassword) {

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent() && passwordEncoder.matches(rawPassword, userOptional.get().getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateRegister(UserDTO userDTO) {

        Optional<UserEntity> userOptionalEmail = userRepository.findByEmail(userDTO.getEmail());
        Optional<UserEntity> userOptionalUsername = userRepository.findByUsername(userDTO.getUsername());

        return !userOptionalEmail.isPresent() && !userOptionalUsername.isPresent();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found"));
    }


}
