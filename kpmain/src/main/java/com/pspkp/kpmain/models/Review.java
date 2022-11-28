package com.pspkp.kpmain.models;


import javax.persistence.Entity;
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

    public Review(){

    }

    

    public Review(Good good, float mark, String text) {
        this.good = good;
        this.mark = mark;
        this.text = text;
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
    
    
}
