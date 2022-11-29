package com.pspkp.kpmain.controllers;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.models.User;
import com.pspkp.kpmain.repo.GoodRepository;
import com.pspkp.kpmain.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;


@Controller
public class GoodsController {
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/goods")
    public String goods(Model model) {
        Iterable<Good> goods = goodRepository.findAll();
        model.addAttribute("goods", goods);
        return "goods";
    }

    @GetMapping("/goods/{id}")
    public String good_details(@PathVariable(value = "id") Long id, Model model) {
        if (!goodRepository.existsById(id)) {
            return "Error";
        }
        Optional<Good> good = goodRepository.findById(id);
        ArrayList<Good> res = new ArrayList<>();
        good.ifPresent(res::add);
        model.addAttribute("good", res);
        model.addAttribute("reviews", res.get(0).getReviews());
        return "good-details";
    }

    @GetMapping("/goods/delete/{id}")
    public String delete_good(@PathVariable(value = "id") Long id, Model model) {
        goodRepository.delete(goodRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return "redirect:/goods";
    }

    @GetMapping("/good/add")
    public String goods_add(Model model) {
        return "goods-add";
    }

    @PostMapping("/good/add")
    public String goods_post_add(@RequestParam String name, String desc, String imageurl, @AuthenticationPrincipal User user, Model model) {
        Good good = new Good(name, desc, imageurl, user);
        user.setCreated(user.getCreated() + 1);
        userRepository.save(user);
        goodRepository.save(good);
        return "redirect:/goods";
    }

    @GetMapping("/goods/s")
    public String good_details(@RequestParam("search") String search, Model model) {
        Iterable<Good> goods = goodRepository.findAll();
        ArrayList<Good> finalgoods = new ArrayList<>();

        for (Good good : goods) {
            if (good.getName().toUpperCase().contains(search.toUpperCase())) {
                finalgoods.add(good);
            }
        }
        model.addAttribute("goods", finalgoods);
        return "goods";
    }

    @GetMapping("/goods/edit/{id}")
    public String edit_good(@PathVariable(value = "id") Long id, Model model) {
        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("good", good);
        return "goods-edit";
    }
    
    @PostMapping("/goods/edit/{id}")
    public String edit_good(@PathVariable(value = "id") Long id, Model model, @RequestParam String name, String desc, String imageurl) {
        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);
        good.setName(name);
        good.setDesc(desc);
        good.setImage(imageurl);
        good.getshort();
        goodRepository.save(good);
        model.addAttribute("good", good);
        model.addAttribute("message", "Изменения сохранены");
        return "goods-edit";
    }

}
