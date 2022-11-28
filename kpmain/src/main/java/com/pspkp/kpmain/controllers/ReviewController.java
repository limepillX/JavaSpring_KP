package com.pspkp.kpmain.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.models.Review;
import com.pspkp.kpmain.models.User;
import com.pspkp.kpmain.repo.GoodRepository;
import com.pspkp.kpmain.repo.ReviewRepository;

@Controller
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GoodRepository goodRepository;

    @GetMapping("/goods/rank/{id}")
    public String rank_good(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("id", id);
        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("good", good);
        return "rank-good";
    }

    @PostMapping("/goods/rank/{id}")
    public String post_rank_good(@PathVariable(value = "id") Long id, Model model, @RequestParam Float mark,
            @RequestParam String text,
            @AuthenticationPrincipal User user) {
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
        Review review = new Review(good, mark, text, user);
        reviewRepository.save(review);
        goodRepository.save(good);

        return "redirect:/goods";
    }

    @GetMapping("/checkreviews")
    public String showreviews(Model model) {
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "showreviews";
    }

    @GetMapping("/goods/YNrank/{id}")
    public String YNRank(@PathVariable(value = "id") Long id, Model model) {
        return "yn-rank-good";
    }

    @PostMapping("/goods/YNrank/{id}")
    public String YNRank(@PathVariable(value = "id") Long id, @RequestParam float question1, float question2,
            float question3, float question4, float question5, Model model, @AuthenticationPrincipal User user) {

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
            if (answers[i] == 1.0)
                text += "<b>Да</b><br><br>";
            else
                text += "<b>Нет</b><br><br>";
        }

        Good good = goodRepository.findById(id).orElseThrow(NoSuchElementException::new);

        int marksamount = good.getMarks_amount();
        float oldmark = good.getMark();
        float totalmark = marksamount * oldmark;
        float newmark = (totalmark + mark) / (marksamount + 1);
        good.setMarks_amount(marksamount + 1);
        good.setMark(newmark);
        goodRepository.save(good);

        reviewRepository.save(new Review(good, mark, text, user));
        return "redirect:/goods/" + id;
    }

}
