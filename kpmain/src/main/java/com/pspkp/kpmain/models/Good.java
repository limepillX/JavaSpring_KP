package com.pspkp.kpmain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String desc;

    private String image;

    private float mark;
    private int marks_amount;

    public Good(){

    }

    public Good(String name, String desc, String image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.mark = 0;
        this.marks_amount = 0;
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

    public void setMark(int mark) {
        this.mark = mark;
    }
}
