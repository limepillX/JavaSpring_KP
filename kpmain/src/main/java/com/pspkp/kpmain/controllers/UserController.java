package com.pspkp.kpmain.controllers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pspkp.kpmain.models.Review;
import com.pspkp.kpmain.models.User;
import com.pspkp.kpmain.repo.ReviewRepository;
import com.pspkp.kpmain.repo.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/userlist")
    public String userlist(Model model) {
        model.addAttribute("users", userRepository.findAll());


        return "userlist";
    }

    @GetMapping("/user/{id}")
    public String show_user(@PathVariable(value = "id") Long id, Model model) {
        
        Iterable<Review> allReviews = reviewRepository.findAll();
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        ArrayList<Review> reviews = new ArrayList<>();

        for (Review review : allReviews){
            if (review.getAuthor().getUsername().equals( user.getUsername()) ){
                reviews.add(review);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        return "showuser";
    }

    @PostMapping("/user/{id}")
    public String edit_user(@PathVariable(value = "id") Long id, @RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("status") String status, Model model) {
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(status);
        userRepository.save(user);
        model.addAttribute("user", user);
        return "showuser";
    }

    @GetMapping("/userlist/s")
    public String user_search(@RequestParam("search") String search, Model model) {
        Iterable<User> users = userRepository.findAll();
        ArrayList<User> finalusers = new ArrayList<>();

        for (User user : users) {
            if (user.getUsername() != null && user.getUsername().toUpperCase().contains(search.toUpperCase())) {
                finalusers.add(user);
            }
        }
        model.addAttribute("users", finalusers);
        return "userlist";
    }

    @GetMapping("/user/{id}/delete")
    public String delete_user(@PathVariable(value = "id") Long id, Model model) {
        userRepository.deleteById(id);
        return "redirect: ../../../../userlist";
    }

    @GetMapping("/user/{id}/ban")
    public String ban_user(@PathVariable(value = "id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        user.setActive(!user.isActive());
        userRepository.save(user);
        model.addAttribute("user", user);
        return "redirect:/user/" + user.getId();
    }

   

    

}
