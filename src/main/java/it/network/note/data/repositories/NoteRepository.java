package it.network.note.data.repositories;

import it.network.note.data.entities.NoteEntity;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteEntity, Long> {




}
