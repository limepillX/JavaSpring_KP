package com.pspkp.kpmain.controllers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pspkp.kpmain.models.Message;
import com.pspkp.kpmain.models.User;
import com.pspkp.kpmain.repo.MessageRepository;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    
    @GetMapping("/showmessages")
    public String showmessages(Model model){

        Iterable<Message> messages = messageRepository.findAll();
        ArrayList<Message> unreadedMessages = new ArrayList<>();
        ArrayList<Message> readedMessages = new ArrayList<>();

        for (Message message : messages){
            if (message.getIsreaded()) readedMessages.add(message);
            else unreadedMessages.add(message);
        }

        model.addAttribute("unreadedMessages", unreadedMessages);
        model.addAttribute("readedMessages", readedMessages);
        return "showmessages";
    }

    @GetMapping("/sendmessage")
    public String sendmessage(Model model) {
        return "sendmessage";
    }

    @PostMapping("/sendmessage")
    public String sendmessage(@RequestParam("text") String text, @AuthenticationPrincipal User user, Model model) {
        messageRepository.save(new Message(text, user));
        return "index";
    }

    @GetMapping("{id}/markasreaded")
    public String markasreaded(@PathVariable(value = "id") Long id, Model model){
        Message message = messageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        message.setIsreaded(true);
        messageRepository.save(message);
        return "redirect:/showmessages";
    }
    

}
