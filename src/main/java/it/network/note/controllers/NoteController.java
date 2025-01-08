package it.network.note.controllers;

//import it.network.note.custom_user_details.CustomUserDetails;
import it.network.note.data.entities.NoteEntity;
import it.network.note.data.entities.UserEntity;
import it.network.note.dto.NoteDTO;
import it.network.note.dto.UserDTO;
import it.network.note.dto.mapper.NoteMapper;
import it.network.note.service.NoteServiceImpl;
//import it.network.note.service.UserService;
import it.network.note.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
        // Získání uživatelských detailů z bezpečnostního kontextu
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Předpokládáme, že máte nějakou službu, která vrátí uživatele podle jména
        UserEntity user = userService.findByUsername(username);  // Získání uživatele z databáze
        Long userId = user.getUserId();  // Získání userId uživatele

        // Načtení poznámek pro daného uživatele
        List<NoteDTO> notes = noteService.getNotesByUserId(userId);

        // Předání poznámek do modelu
        model.addAttribute("notes", notes);
        return "pages/note/home";
    }



    @GetMapping("/create")
    public String renderCreatePage(@ModelAttribute NoteDTO noteDTO) {
        return "pages/note/create";
    }

    @PostMapping("/create")
    public String createNote(@Valid @ModelAttribute NoteDTO noteDTO,UserEntity user, BindingResult result) {
        if (result.hasErrors())
            return "pages/note/create";
        noteService.saveNewNote(noteDTO);
        return "redirect:/home";
    }

    @DeleteMapping("/delete")
    public String deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return "redirect:/home";
    }

    @GetMapping("/details")
    public String renderDetailPage(@PathVariable Long noteId, Model model) {
        NoteDTO note = noteService.getById(noteId);
        model.addAttribute("notes", note);
        return "pages/note/details";
    }

    @GetMapping("/edit")
    public String renderEditPage(@PathVariable Long noteId, Model model) {
        NoteDTO fetchedNote = noteService.getById(noteId);

        model.addAttribute("noteDTO", fetchedNote);

        return "pages/note/edit";
    }

    @PostMapping("edit")
    public String editNote(@PathVariable Long noteId,
                           @Valid @ModelAttribute NoteDTO noteDTO,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            model.addAttribute("noteDTO", noteDTO);
            return "pages/note/edit";
        }

        noteDTO.setNoteId(noteId);
        noteService.edit(noteDTO);
        return "redirect:/home";
    }


}



