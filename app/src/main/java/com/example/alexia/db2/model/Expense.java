package com.example.alexia.db2.model;

/**
 * Created by alexia on 16/12/2016.
 */

public class Expense {
    private String name;
    private String description;
    private float amount;
    private String datetime;
    private int id;

    //constructors
    public Expense(){

    }
    public Expense(String name,float amount,String datetime){
        this.name=name;
        this.amount=amount;
        this.datetime=datetime;
    }
    public Expense(String name,float amount,String description,String datetime){
        this.name=name;
        this.amount=amount;
        this.description=description;
        this.datetime=datetime;
    }
    public Expense(int id){
        this.id=id;
    }
    //getters,setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "Amount: "+getAmount()+"\nDate: "+getDatetime()+"\n Expense Details: "+getDescription();
    }
}
