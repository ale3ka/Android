package com.example.alexia.db2.model;

/**
 * Created by alexia on 16/12/2016.
 */

public class Category {
    private String name;
    private String description;
    private int id;

    //constructors
    public Category(){

    }

    public Category(String name){
        this.name=name;
    }

    public Category(String name,String description){
        this.name=name;
        this.description=description;
    }
    public Category(int id,String name){
        this.name=name;
        this.id=id;
    }
    public Category(int id,String name,String description){
        this.name=name;
        this.id=id;
        this.setDescription(description);
    }
    //getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){

        return "Category: "+getName()+"\nCategoy details: "+getDescription()+"\n";
    }
}
