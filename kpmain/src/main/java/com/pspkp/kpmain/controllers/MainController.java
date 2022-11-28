package com.pspkp.kpmain.controllers;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.models.Review;
import com.pspkp.kpmain.repo.GoodRepository;
import com.pspkp.kpmain.repo.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class MainController {

    private boolean as_admin = false;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("as_admin", as_admin);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("as_admin", as_admin);
        return "login";
    }

    @PostMapping("/login")
    public String login_post(Model model, @RequestParam String login, String pass) {
        if (Objects.equals(login, "admin") && Objects.equals(login, "admin")) {
            as_admin = true;
            return "redirect:/goods";
        } else {
            return "/login";
        }

    }

    @GetMapping("/logout")
    public String logout(Model model) {
        as_admin = false;
        return "redirect:/goods";
    }

    @GetMapping("/goods")
    public String goods(Model model) {
        Iterable<Good> goods = goodRepository.findAll();
        model.addAttribute("as_admin", as_admin);
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
        model.addAttribute("as_admin", as_admin);
        model.addAttribute("good", res);
        model.addAttribute("reviews", res.get(0).getReviews());
        return "good-details";
    }

    @GetMapping("/goods/rank/{id}")
    public String rank_good(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("id", id);
        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("as_admin", as_admin);
        model.addAttribute("good", good);
        return "rank-good";
    }

    @GetMapping("/goods/delete/{id}")
    public String delete_good(@PathVariable(value = "id") Long id, Model model) {
        if (as_admin) {
            goodRepository.deleteById(id);
            return "redirect:/goods";
        } else
            return "Error";

    }

    @PostMapping("/goods/rank/{id}")
    public String post_rank_good(@PathVariable(value = "id") Long id, Model model, @RequestParam Float mark,
            @RequestParam String text) {
        if (!goodRepository.existsById(id)) {
            return "Error";
        }
        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);
        int marksamount = good.getMarks_amount();
        float oldmark = good.getMark();
        float totalmark = marksamount * oldmark;
        float newmark = (totalmark + mark) / (marksamount + 1);
        good.setMarks_amount(marksamount + 1);
        good.setMark(newmark);
        Review review = new Review(good, mark, text);
        reviewRepository.save(review);
        goodRepository.save(good);

        return "redirect:/goods";
    }

    @GetMapping("/goods/add")
    public String goods_add(Model model) {
        model.addAttribute("as_admin", as_admin);
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
        model.addAttribute("as_admin", as_admin);
        model.addAttribute("goods", finalgoods);
        return "goods";
    }

    @GetMapping("/checkreviews")
    public String showreviews(Model model) {
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "showreviews";
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

    @GetMapping("/goods/YNrank/{id}")
    public String YNRank(@PathVariable(value = "id") Long id, Model model) {
        return "yn-rank-good";
    }

    @PostMapping("/goods/YNrank/{id}")
    public String YNRank(@PathVariable(value = "id") Long id, @RequestParam float question1, float question2,
            float question3, float question4, float question5, Model model) {

        String[] questions = {
                "1. Вы довольны качеством товара?",
                "2. Вы посоветуете купленный товар другу?",
                "3. Товар не прибыл бракованным?",
                "4. Соответствует ли цена товара его качеству?",
                "5. Соответсвует ли товар его описанию?"
        };

        float[] answers = { question1, question2, question3, question4, question5 };
        float mark = 0;
        for (float answer : answers) {
            mark += answer;
        }
        String text = "";

        for (int i = 0; i < 5; i++) {
            text += questions[i] + "<br>";
            if(answers[i] == 1.0) text+= "<b>Да</b><br><br>";
            else text+= "<b>Нет</b><br><br>";
        }

        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);

        int marksamount = good.getMarks_amount();
        float oldmark = good.getMark();
        float totalmark = marksamount * oldmark;
        float newmark = (totalmark + mark) / (marksamount + 1);
        good.setMarks_amount(marksamount + 1);
        good.setMark(newmark);
        goodRepository.save(good);

        reviewRepository.save(new Review(good, mark, text));
        return "redirect:/goods/" + id;
    }
}
