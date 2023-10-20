package ru.dorin.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dorin.spring.models.Book;
import ru.dorin.spring.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {

        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(String name) {
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Book WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class));
        if (bookList.size() == 0) return null;
        return bookList.get(0);
    }

    public Book show(int id) {
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        if (bookList.size() == 0) return null;
        Book book = bookList.get(0);
        return book;
    }



    public Person getHandler(int bookId) {
        List<Person> personList = jdbcTemplate.query("select id, name, email, age from person where id in (select coalesce (bookhandler_id, 0) from book where id=?)", new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class));
        Person person = personList.get(0);
        return person;
    }


    public void save(Book book) {

        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());

    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE INTO Book SET name=?, author=?, year=? WHERE id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public Book toHand(int id, Person person) {
        Book book = show(id);
        jdbcTemplate.update("UPDATE Book SET bookHandler_id=? WHERE id=?",
                person.getId(), id);
        return book;
    }

    public Book returnFromHandler(int id) {
        Book book = show(id);
        jdbcTemplate.update("UPDATE Book SET bookHandler_id=? WHERE id=?",
                0, id);
        return book;
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    ////////////////////////
    //тестируем производительность batchUpdate
    ///////////////////////


}
