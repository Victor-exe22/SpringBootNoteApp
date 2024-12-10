package it.network.note.data.repositories;

import it.network.note.data.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {




}
