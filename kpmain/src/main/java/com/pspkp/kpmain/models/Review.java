package com.pspkp.kpmain.models;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="good_id", nullable=false)
    private Good good;

    private float mark;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User author;

    public Review(){

    }

    

    public Review(Good good, float mark, String text, User author) {
        this.good = good;
        this.mark = mark;
        this.text = text;
        this.author = author;
    }


    public String getAuthorName(){
        if (author.getUsername() == null){
            return "unnamed";
        }
        return author.getUsername();
    }
    public float getMark() {
        return mark;
    }
    public void setMark(float mark) {
        this.mark = mark;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Long getId() {
        return id;
    }
    public Good getGood(){
        return good;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    
    
}
