package ru.dorin.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dorin.spring.models.Book;
import ru.dorin.spring.models.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){

        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public List<Book> bookList (int id){
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        if(bookList.size() == 0) return null;
        return bookList;
    }
    public Person show(String email){
        List<Person> personList = jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class));
        if(personList.size() == 0) return null;
        return personList.get(0);
    }
    public Person show(int id){
        List<Person> personList = jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
        if(personList.size() == 0) return null;
        return personList.get(0);
    }


    public void save(Person person) {

        jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(), person.getEmail());

    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE INTO Person SET name=?, age=?, email=? WHERE id=?",
                person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    ////////////////////////
    //тестируем производительность batchUpdate
    ///////////////////////

    public void testMultipleUpdate(){
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        for(Person person : people){
            jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time:" + (after-before));

    }
    public void testBatchUpdate(){
        final List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        for(Person person : people){
            jdbcTemplate.batchUpdate("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, people.get(i).getName()+"a");
                            ps.setInt(2, people.get(i).getAge());
                            ps.setString(3, people.get(i).getEmail()+"a");

                        }

                        @Override
                        public int getBatchSize() {
                            return people.size();
                        }
                    });

        }
        long after = System.currentTimeMillis();
        System.out.println("Time:" + (after-before));
    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name"+i, 30, "test"+i+"@mail.com"));
        }
        return people;
    }

}
