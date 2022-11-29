package com.pspkp.kpmain.controllers;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.repo.GoodRepository;
import com.pspkp.kpmain.repo.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/showanalytics")
    public String showanalytics(Model model) {
        Iterable<Good> goods = goodRepository.findAll();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Float> marks = new ArrayList<>();
        long totalmarks = reviewRepository.count();
        long totalgoods = goodRepository.count();

        for (Good good : goods) {
            names.add(good.getName());
            marks.add(good.getMark());
        }


        model.addAttribute("totalmarks", totalmarks);
        model.addAttribute("totalgoods", totalgoods);
        model.addAttribute("names", String.join(",,,", names)).toString();
        model.addAttribute("marks", marks.toString());
        return "GoodsAnalytics";
    }

}
