package it.network.note.data.entities;

import jakarta.persistence.*;

@Entity
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

}
