package be.continuum.springsecurity.register;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationUser {

    @NotBlank(message = "Email is a required field")
    private String email;

    @NotBlank(message = "Name is a required field")
    private String name;

    @Size(min = 5, max = 128, message = "Passwords need to be between 5 and 128 characters long")
    private String password;

    private String repeatPassword;

    void clearPasswords() {
        this.password = null;
        this.repeatPassword = null;
    }
}
