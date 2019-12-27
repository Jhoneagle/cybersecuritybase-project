package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sec.project.domain.models.*;
import sec.project.service.MainService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LogicController {
    @Autowired
    private MainService mainService;

    @GetMapping("/blogger/feed/{id}")
    public String mainPage(Model model, @PathVariable Long id) {
        BlogInfo blog = mainService.getBlogDetails(id);
        model.addAttribute("blog", blog);
        model.addAttribute("owner", this.mainService.isOwnerOfBlog(id));
        model.addAttribute("id", id);
        return "main-page";
    }

    @GetMapping("/blogger/feed/{id}/follow")
    public String follow(@PathVariable Long id) {
        this.mainService.followPerson(id);
        return "redirect:/blogger/feed/" + id;
    }

    @GetMapping("/blogger/feed/{id}/unfollow")
    public String unfollow(@PathVariable Long id) {
        this.mainService.unFollowPerson(id);
        return "redirect:/blogger/feed/" + id;
    }

    @GetMapping("/blogger/feed/posts/{postId}")
    public String fullPost(Model model, @PathVariable Long postId) {
        BlogPostModel blog = mainService.getFullPost(postId);
        model.addAttribute("blog", blog);
        model.addAttribute("userId", mainService.getCreatorId(postId));
        return "full-blog-post";
    }

    @PostMapping("/blogger/feed/{accountId}/{postId}/remove")
    public String removePost(@PathVariable Long accountId, @PathVariable Long postId) {
        mainService.removePost(postId);
        return "redirect:/blogger/feed/" + accountId;
    }

    @PostMapping("/blogger/feed/{accountId}/{postId}/like")
    public String likePost(@PathVariable Long accountId, @PathVariable Long postId) {
        mainService.likePost(postId);
        return "redirect:/blogger/feed/posts/" + postId;
    }

    @PostMapping("/blogger/feed/{accountId}/{postId}/unlike")
    public String unlikePost(@PathVariable Long accountId, @PathVariable Long postId) {
        mainService.unlikePost(postId);
        return "redirect:/blogger/feed/posts/" + postId;
    }

    @GetMapping("/blogger/create/post")
    public String createPost(@ModelAttribute PostValidator postValidator) {
        return "create-post";
    }

    @PostMapping("/blogger/create/post")
    public String createPost(@Valid @ModelAttribute PostValidator postValidator, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "create-post";
        }

        this.mainService.createPost(postValidator);
        return "redirect:/home";
    }

    @PostMapping("/blogger/searchPeople")
    public String searchPeople(@RequestParam String searchField) {
        return "redirect:/blogger/searchPeople/" + searchField;
    }

    @GetMapping("/blogger/searchPeople/{param}")
    public String searchPeople(Model model, @PathVariable String param) {
        List<UserModel> people = this.mainService.findPeopleWithParam(param);
        model.addAttribute("result", people);
        return "search-page-people";
    }

    @PostMapping("/blogger/searchPosts")
    public String searchPosts(@RequestParam String searchField) {
        return "redirect:/blogger/searchPosts/" + searchField;
    }

    @GetMapping("/blogger/searchPosts/{param}")
    public String searchPosts(Model model, @PathVariable String param) {
        List<BlogPost> posts = this.mainService.findPostsWithParam(param);
        model.addAttribute("result", posts);
        return "search-page-posts";
    }
}
