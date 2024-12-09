package it.network.note.dto.mapper;

import it.network.note.data.entities.NoteEntity;
import it.network.note.dto.NoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteEntity toEntity(NoteDTO noteDTO);

    NoteDTO toDTO(NoteEntity noteEntity);

    void updateNoteDTO(NoteDTO source, @MappingTarget NoteDTO target);

    void updateNoteEntity(NoteDTO source,@MappingTarget NoteEntity target);
}
