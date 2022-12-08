package com.pspkp.kpmain.models;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String desc;

    private String shortdesc;

    private String image;

    private float mark;
    private int marks_amount;

    private ArrayList<String> recommendations;

    @OneToMany(mappedBy="good", cascade = CascadeType.REMOVE)
    private Set<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User author;

    public Good(){

    }

    public Good(String name, String desc, String image, User user) {
        this.name = name;
        this.desc = desc;

        getshort();

        this.image = image;
        this.mark = 0;
        this.marks_amount = 0;
        this.author = user;
        this.recommendations = new ArrayList<String>();
    }

    public void getshort(){
        if(desc.length() >= 50){
            this.shortdesc = desc.substring(0, 50) + "...";
        } else this.shortdesc = desc;
    }

    public int getMarks_amount() {
        return marks_amount;
    }

    public void setMarks_amount(int marks_amount) {
        this.marks_amount = marks_amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ArrayList<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(ArrayList<String> recommendations) {
        this.recommendations = recommendations;
    }
    
}

