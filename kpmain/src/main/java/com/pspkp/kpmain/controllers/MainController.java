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
        return "Error";
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

    

    @GetMapping("/showanalytics")
    public String showanalytics(Model model) {
        Iterable<Good> goods = goodRepository.findAll();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Float> marks = new ArrayList<>();

        for (Good good : goods) {
            names.add(good.getName());
            marks.add(good.getMark());
        }

        model.addAttribute("names", String.join(",", names)).toString();
        model.addAttribute("marks", marks.toString());
        return "GoodsAnalytics";
    }

    
}
