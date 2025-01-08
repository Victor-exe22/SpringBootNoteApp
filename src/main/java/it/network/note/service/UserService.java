package it.network.note.service;

import it.network.note.data.entities.UserEntity;
import it.network.note.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void create(UserDTO user, boolean isAdmin);


    boolean validateLogin(String email, String rawPassword);

    boolean validateRegister(UserDTO userDTO);

    UserEntity findByUsername(String username);
}
