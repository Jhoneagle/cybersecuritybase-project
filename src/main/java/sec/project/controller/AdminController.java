package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sec.project.domain.models.UpdatePasswordValidator;
import sec.project.service.AccountService;
import sec.project.service.MainService;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MainService mainService;

    @GetMapping("/blogger/password/update")
    public String redirectPasswordChange() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return "redirect:/blogger/" + username + "/password/update";
    }

    @GetMapping("/blogger/{username}/password/update")
    public String changePassword(Model model, @PathVariable String username, @ModelAttribute UpdatePasswordValidator updatePasswordValidator) {
        model.addAttribute("id", username);
        return "change-password";
    }

    @PostMapping("/blogger/{username}/password/update")
    public String changePassword(@PathVariable String username, @Valid @ModelAttribute UpdatePasswordValidator updatePasswordValidator, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "change-password";
        }

        this.accountService.updatePassword(username, updatePasswordValidator.getPassword());

        boolean admin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));

        if (admin) {
            return "redirect:/blogger/admin/users";
        } else {
            return "redirect:/home";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blogger/admin/users")
    public String adminPanelUsers(Model model) {
        model.addAttribute("users", this.accountService.getAllUsers());
        return "admin-panel-users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blogger/admin/posts")
    public String adminPanelPosts(Model model) {
        model.addAttribute("posts", this.mainService.getAllPosts());
        return "admin-panel-posts";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blogger/admin/feed/{postId}")
    public String removePost(@PathVariable Long postId) {
        this.mainService.removePost(postId);
        return "redirect:/blogger/admin/posts";
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blogger/admin/control/{username}")
    public String removeUser(@PathVariable String username) {
        this.mainService.removeAll(username);
        this.accountService.removeUser(username);
        return "redirect:/blogger/admin/users";
    }
}
