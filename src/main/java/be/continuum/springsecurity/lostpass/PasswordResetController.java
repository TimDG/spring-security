package be.continuum.springsecurity.lostpass;

import be.continuum.springsecurity.post.ResetPassword;
import be.continuum.springsecurity.user.BlogUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/lostPassword")
public class PasswordResetController {

    private final PasswordTokenService passwordTokenService;

    public PasswordResetController(PasswordTokenService passwordTokenService) {
        this.passwordTokenService = passwordTokenService;
    }

    @GetMapping
    public String lostPassword() {
        return "component/lostPassword.html";
    }

    @PostMapping
    public String sendReset(String email) {
        passwordTokenService.sendPasswordReset(email);
        return "redirect:/login";
    }

    @GetMapping("/reset")
    public ModelAndView resetPassword(String token) {
        passwordTokenService.checkToken(token);

        return new ModelAndView("component/resetPassword.html", "token", new ResetPassword(token));
    }

    @PostMapping("/reset")
    public ModelAndView resetPassword(@Valid @ModelAttribute("token") ResetPassword token, BindingResult result) {
        verifyPasswordMatch(token, result);

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("component/resetPassword.html");
            modelAndView.addObject("token", token);
            modelAndView.addObject("errors", result.getAllErrors());
            return modelAndView;
        }

        passwordTokenService.updatePassword(token.getToken(), token.getPassword());

        return new ModelAndView("redirect:/login");
    }

    private void verifyPasswordMatch(@ModelAttribute("token") @Valid ResetPassword token, BindingResult result) {
        if (!Objects.equals(token.getPassword(), token.getRepeatPassword())) {
            result.rejectValue("password", "nomatch", "Passwords don't match");
            result.rejectValue("repeatPassword", "nomatch", "Passwords don't match");
            token.clearPasswords();
        }
    }
}
