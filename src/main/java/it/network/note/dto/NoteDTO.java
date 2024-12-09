package it.network.note.dto;


import jakarta.validation.constraints.NotBlank;


public class NoteDTO {

    private long noteId;

    @NotBlank(message = "Vypln")
    private String title;

    @NotBlank(message = "Vypln")
    private String description;

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public @NotBlank(message = "Vypln") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Vypln") String description) {
        this.description = description;
    }

    public @NotBlank(message = "Vypln") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Vypln") String title) {
        this.title = title;
    }
}
