package com.pspkp.kpmain.controllers;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.repo.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private GoodRepository goodRepository;

    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("title", "Главная страница");
        return "index";
    }

    @GetMapping("/goods")
    public String goods(Model model) {
        Iterable<Good> goods = goodRepository.findAll();
        model.addAttribute("goods", goods);
        return "goods";
    }

    @GetMapping("/goods/{id}")
    public String good_details(@PathVariable(value = "id") Long id, Model model) {
        Optional<Good> good = goodRepository.findById(id);
        ArrayList<Good> res = new ArrayList<>();
        good.ifPresent(res::add);
        model.addAttribute("good", good);
        return "goods";
    }
    @GetMapping("/goods/add")
    public String goods_add(Model model) {
        return "goods-add";
    }
    @PostMapping("/goods/add")
    public String goods_post_add(@RequestParam String name, String desc, String imageurl, Model model) {
        Good good = new Good(name, desc, imageurl);
        goodRepository.save(good);
        return "redirect:/goods";
    }

}