package it.network.note.controllers;

import it.network.note.data.entities.UserEntity;
import it.network.note.dto.NoteDTO;
import it.network.note.dto.mapper.NoteMapper;
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
    private  NoteServiceImpl noteService;

    @Autowired
    private NoteMapper noteMapper;

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
    public String createNote(@Valid @ModelAttribute NoteDTO noteDTO,UserEntity user, BindingResult result) {
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
        // Kontrola, zda je uživatel přihlášen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";  // Pokud není přihlášen, přesměruj na přihlašovací stránku
        }

        // Získání poznámky podle ID
        NoteDTO note = noteService.getById(noteId);

        // Pokud poznámka neexistuje, můžete přesměrovat na stránku s chybou
        if (note == null) {
            return "redirect:/error";  // Můžete přesměrovat na stránku s chybou nebo jinam
        }

        // Přidání poznámky do modelu pro zobrazení
        model.addAttribute("notes", note);
        return "pages/note/details";  // Přejdeme na stránku s detaily poznámky
    }


//    @GetMapping("/edit")
//    public String renderEditPage(@PathVariable Long noteId, Model model) {
//        NoteDTO fetchedNote = noteService.getById(noteId);
//
//        model.addAttribute("noteDTO", fetchedNote);
//
//        return "pages/note/edit";
//    }
//
//    @PostMapping("/edit")
//    public String editNote(@PathVariable Long noteId,
//                           @Valid @ModelAttribute NoteDTO noteDTO,
//                           BindingResult result,
//                           Model model) {
//
//        if (result.hasErrors()) {
//            model.addAttribute("noteDTO", noteDTO);
//            return "pages/note/edit";
//        }
//
//        noteDTO.setNoteId(noteId);
//        noteService.edit(noteDTO);
//        return "redirect:/home";
//    }
}



