package it.network.note.controllers;

import it.network.note.data.entities.NoteEntity;
import it.network.note.dto.NoteDTO;
import it.network.note.service.NoteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class NoteController {

    private final NoteServiceImpl noteService;



    @Autowired
    public NoteController(NoteServiceImpl noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/home")
    public String getAllNotes(Model model) {
        List<NoteDTO> notes = noteService.getAll();
        model.addAttribute("notes", notes);
        return "pages/home";

    }




}
