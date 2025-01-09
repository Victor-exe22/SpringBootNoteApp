package it.network.note.dto;


import it.network.note.data.entities.NoteEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class NoteDTO {

    private Long noteId;

    @NotBlank(message = "Vypln")
    @Size(max = 20)
    private String title;

    @NotBlank(message = "Vypln")
    @Size(max = 50)
    private String description;

    public NoteDTO(Long noteId, String title, String description) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
