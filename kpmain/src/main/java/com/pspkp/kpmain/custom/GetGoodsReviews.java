package com.pspkp.kpmain.custom;

import java.util.ArrayList;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.models.Review;
import com.pspkp.kpmain.models.User;

public class GetGoodsReviews{
    public static ArrayList<Good> Getgoods(Iterable<Good> allGoods, User user){
        ArrayList<Good> goods = new ArrayList<>();
        for (Good good : allGoods){
            if (good.getAuthor().getUsername().equals( user.getUsername()) ){
                goods.add(good);
            }
        }
        return goods;
    }

    public static ArrayList<Review> getrReviews(Iterable<Review> allReviews, User user){
        ArrayList<Review> reviews = new ArrayList<>();
        for (Review review : allReviews){
            if (review.getAuthor().getUsername().equals( user.getUsername()) ){
                reviews.add(review);
            }
        }
        return reviews;
    }
}