package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sec.project.domain.models.UserValidator;
import sec.project.service.AccountService;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String defaultMapping() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String logedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = this.accountService.findByUsername(auth.getName()).getId();

        return "redirect:/blogger/feed/" + id;
    }

    @GetMapping("/register")
    public String register(@ModelAttribute UserValidator userValidator) {
        return "register";
    }

    /**
     * Custom end point for registration.
     * Validates the users input in registration form and returns appropriate error messages if needed.
     * Otherwise creates the new user.
     *
     * @see UserValidator
     *
     * @return redirect to login endpoint.
     */
    @PostMapping("/register")
    public String createAccount(@Valid @ModelAttribute UserValidator userValidator, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "register";
        }

        this.accountService.create(userValidator);
        return "redirect:/login";
    }
}
