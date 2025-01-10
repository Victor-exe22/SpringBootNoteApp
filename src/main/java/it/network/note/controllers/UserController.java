package it.network.note.controllers;

import it.network.note.dto.UserDTO;
import it.network.note.service.UserService;
import it.network.note.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String renderLoginPage() {
        return "/pages/user/login";
    }

    @PostMapping("/login")
    public String signIn(@Valid @ModelAttribute UserDTO userDTO,
                         UserServiceImpl userService) {

        if (userService.validateLogin(userDTO.getEmail(), userDTO.getPassword())) {
            return "redirect:/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String renderRegisterPage(@ModelAttribute UserDTO userDTO) {
        return "/pages/user/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return renderRegisterPage(userDTO);
        }

        if (!userService.validateRegister(userDTO)) {
            redirectAttributes.addFlashAttribute("error", "Email or username already exist");
            return renderRegisterPage(userDTO);
        }

        userService.create(userDTO, false);

        return "redirect:/login";
    }




}
