package ru.dorin.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dorin.spring.dao.BookDAO;
import ru.dorin.spring.dao.PersonDAO;
import ru.dorin.spring.models.Book;
import ru.dorin.spring.models.Person;

@Component
public class BookValidator implements Validator
{
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookDAO.show(book.getName()) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }
        if (book.getBookHandlerId() != 0) {
            errors.rejectValue("bookHandlerId", "", "This book is already taken");
        }
    }
    public void validateDeleteHandler(Object target, Errors errors) {
        Book book = (Book) target;
        if (book.getBookHandlerId() == 0) {
            errors.rejectValue("bookHandlerId", "", "This book is not taken");
        }
    }
}
