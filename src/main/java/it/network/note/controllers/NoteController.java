package it.network.note.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class NoteController {

    @GetMapping("/home")
    public String renderHome() {
        return "pages/home";
    }
}
