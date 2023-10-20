package ru.dorin.spring.models;


import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;
    @NotNull(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 char")
    private String name;
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    public Person(int id, String name, int age, String mail) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = mail;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
