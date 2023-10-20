package ru.dorin.spring.models;

import ru.dorin.spring.dao.PersonDAO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    @NotNull(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 char")
    private String name;

    @NotNull(message = "Author should not be empty")
    @Size(min = 2, max = 50, message = "Author name should be between 2 and 50 char")
    private String author;
    @Min(value = 0, message = "Year should be greater than 0")
    private int year;

    private int bookHandlerId;

//    public Book(int id, String name, String author, int year) {
//        this.id = id;
//        this.name = name;
//        this.author = author;
//        this.year = year;
//        this.bookHandlerId=0;
//    }
    public Book(int id, String name, String author, int year, int bookHandlerId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.bookHandlerId=bookHandlerId;
    }

    public Book(){}

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBookHandlerId() {
        return bookHandlerId;
    }


    public void setBookHandler(int bookHandlerId) {
        this.bookHandlerId = bookHandlerId;
    }

}
