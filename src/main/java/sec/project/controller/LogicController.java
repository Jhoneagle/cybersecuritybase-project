package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.models.BlogInfo;
import sec.project.domain.models.BlogPost;
import sec.project.domain.models.BlogPostModel;
import sec.project.domain.models.UserModel;
import sec.project.service.MainService;

import java.util.List;

@Controller
public class LogicController {
    @Autowired
    private MainService mainService;

    @GetMapping("/blogger/feed/{id}")
    public String mainPage(Model model, @PathVariable Long id) {
        BlogInfo blog = mainService.getBlogDetails(id);
        model.addAttribute("blog", blog);
        return "main-page";
    }

    @GetMapping("/blogger/feed/{accountId}/{postId}")
    public String mainPage(Model model, @PathVariable Long accountId, @PathVariable Long postId) {
        BlogPostModel blog = mainService.getFullPost(accountId, postId);
        model.addAttribute("blog", blog);
        return "full-blog-post";
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
