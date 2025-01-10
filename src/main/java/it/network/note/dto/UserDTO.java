package it.network.note.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Enter your user name")
    private String username;

    @NotBlank(message = "Enter your email")
    @Email()
    private String email;

    @NotBlank()
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank()
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String confirmPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
