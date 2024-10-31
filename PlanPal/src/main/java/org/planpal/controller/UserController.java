package org.planpal.controller;

import jakarta.validation.Valid;
import org.planpal.dto.DailyEventDTO;
import org.planpal.dto.UserDTO;
import org.planpal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("userDTO")
                                    @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userService.save(userDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<UserDTO> showUserForm(@PathVariable int userId) {
        UserDTO user = userService.findByUserId(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get/name/{userId}")
    public ResponseEntity<UserDTO> showUserNameForm(@PathVariable int userId) {
        UserDTO user = userService.findByUserId(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
