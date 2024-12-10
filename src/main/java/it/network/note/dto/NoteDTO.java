package it.network.note.dto;


import jakarta.validation.constraints.NotBlank;


public class NoteDTO {


    private Long noteId;

    @NotBlank(message = "Vypln")
    private String title;

    @NotBlank(message = "Vypln")
    private String description;

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

    @Override
    public String toString() {
        return "NoteDTO{" +
                "noteId=" + noteId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
