package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sec.project.service.AccountService;
import sec.project.service.MainService;

@Controller
public class AdminController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MainService mainService;


}
