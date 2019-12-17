package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.models.BlogPost;
import sec.project.domain.models.UserModel;
import sec.project.service.MainService;

import java.util.List;

@Controller
public class LogicController {
    @Autowired
    private MainService mainService;

    @GetMapping("/blogger/feed/{id}")
    public String mainPage(@PathVariable Long id) {
        return "done";
    }

    /**
     * Returns search page showing there the result that was gotten by the parameter user gave.
     * In this situations the list of persons whose first and/or last name withs the search parameter.
     *
     * @param model model object
     * @param searchField search parameter
     * @return template name.
     */
    @PostMapping("/old-face/searchPeople")
    public String searchPeople(Model model, @RequestParam String searchField) {
        List<UserModel> people = this.mainService.findPeopleWithParam(searchField);

        model.addAttribute("result", people);
        return "search-page-people";
    }

    /**
     * Returns search page showing there the result that was gotten by the parameter user gave.
     * In this situations the list of persons whose first and/or last name withs the search parameter.
     *
     * @param model model object
     * @param searchField search parameter
     * @return template name.
     */
    @PostMapping("/old-face/searchPosts")
    public String searchPosts(Model model, @RequestParam String searchField) {
        List<BlogPost> people = this.mainService.findPostsWithParam(searchField);

        model.addAttribute("result", people);
        return "search-page-posts";
    }
}
