package be.continuum.springsecurity.post;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ResetPassword {
    private String token;

    @Size(min = 5, max = 128, message = "Passwords need to be between 5 and 128 characters long")
    private String password;

    private String repeatPassword;

    public ResetPassword(String token) {
        this.token = token;
    }

    public void clearPasswords() {
        this.password = null;
        this.repeatPassword = null;
    }
}
