package com.model;


import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.persistence.Entity;

public class Filter {
    private String name;
    private int age;

    public Filter() {
    }

    public Filter(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
