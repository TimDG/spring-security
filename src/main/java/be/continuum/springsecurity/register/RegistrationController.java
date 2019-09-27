package be.continuum.springsecurity.register;

import be.continuum.springsecurity.user.BlogUserDetailService;
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
@RequestMapping("/register")
public class RegistrationController {

    private final BlogUserDetailService userDetailService;

    public RegistrationController(BlogUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping
    public ModelAndView register() {
        return new ModelAndView("component/register",
                "user",
                new RegistrationUser());
    }

    @PostMapping
    public ModelAndView register(@Valid @ModelAttribute("user") RegistrationUser user, BindingResult result) {
        if (!Objects.equals(user.getPassword(), user.getRepeatPassword())) {
            result.rejectValue("password", "nomatch", "Passwords don't match");
            result.rejectValue("repeatPassword", "nomatch", "Passwords don't match");
        }

        if (result.hasErrors()) {
            user.clearPasswords();

            ModelAndView view = new ModelAndView("component/register");
            view.addObject("user", user);
            view.addObject("errors", result.getAllErrors());
            return view;
        }

        userDetailService.register(user);

        return new ModelAndView("redirect:/login");
    }
}
