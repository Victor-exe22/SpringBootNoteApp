package it.network.note.data.repositories;

import it.network.note.data.entities.UserEntity;
import it.network.note.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);












}
