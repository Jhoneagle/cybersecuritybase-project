package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sec.project.domain.AccountModel;
import sec.project.service.AccountService;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/")
    public String defaultMapping() {
        return "index";
    }

    /**
     * Custom end point for login.
     *
     * @return template name.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/home")
    public String logedIn() {
        return "done";
    }

    /**
     * Custom end point for registration.
     *
     * @return template name.
     */
    @GetMapping("/register")
    public String register(@ModelAttribute AccountModel accountModel) {
        return "register";
    }

    /**
     * Custom end point for registration.
     * Validates the users input in registration form and returns appropriate error messages if needed.
     * Otherwise creates the new user.
     *
     * @see AccountModel
     *
     * @return template name.
     */
    @PostMapping("/register")
    public String createAccount(@Valid @ModelAttribute AccountModel accountModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "register";
        }

        this.accountService.create(accountModel);
        return "redirect:/login";
    }
}
