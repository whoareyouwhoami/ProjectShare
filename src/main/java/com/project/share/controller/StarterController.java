package com.project.share.controller;

import com.project.share.model.User;
import com.project.share.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class StarterController {
    static final Logger log = LoggerFactory.getLogger(StarterController.class);

    private final UserService userService;

    StarterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginProcess() {
        return "/starter/login";
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return new ModelAndView("/starter/register", "UserForm", new User());
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("UserForm") @Valid User user, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("starter/register");

        if (bindingResult.hasErrors()) {
            log.error("Registration binding form error");
            mv.addObject("UserForm", user);
            return mv;
        }

        try {
            User registeredUser = userService.userAdd(user);
            userService.authenticateLogin(registeredUser, user.getPassword());
            mv.setViewName("redirect:/home");
        } catch (Exception e) {
            e.printStackTrace();
            user.setPassword("");
            user.setPasswordConfirm("");
            log.error("Registration error");
        }
        return mv;
    }

    @GetMapping("/home")
    public String home(Principal principal) {
        log.info("Logged in user: " + principal.getName());
        return "main/home";
    }
}
