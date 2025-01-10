package it.network.note.controllers;

import it.network.note.data.entities.UserEntity;
import it.network.note.dto.NoteDTO;
import it.network.note.dto.mapper.NoteMapper;
import it.network.note.service.NoteService;
import it.network.note.service.NoteServiceImpl;
import it.network.note.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public String renderHomePage(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        UserEntity user = userService.findByUsername(username);
        Long userId = user.getUserId();

        List<NoteDTO> notes = noteService.getNotesByUserId(userId);

        model.addAttribute("notes", notes);
        return "pages/note/home";
    }

    @GetMapping("/create")
    public String renderCreatePage(@ModelAttribute NoteDTO noteDTO) {
        return "pages/note/create";
    }

    @PostMapping("/create")
    public String createNote(@Valid @ModelAttribute NoteDTO noteDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/create";
        }else {
            noteService.saveNewNote(noteDTO);
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return "redirect:/home";
    }

    @GetMapping("/details")
    public String renderDetailPage(@RequestParam("noteId") Long noteId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        NoteDTO note = noteService.getById(noteId);

        if (note == null) {
            return "redirect:/error";
        }

        model.addAttribute("notes", note);
        return "pages/note/details";
    }
}



