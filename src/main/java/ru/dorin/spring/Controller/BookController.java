package ru.dorin.spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dorin.spring.dao.BookDAO;
import ru.dorin.spring.dao.PersonDAO;
import ru.dorin.spring.models.Book;
import ru.dorin.spring.models.Person;
import ru.dorin.spring.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "book/index";
    }
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("person", bookDAO.getHandler(id));
        return "book/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "book/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "book/new";

        bookDAO.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }
    @GetMapping("/{id}/toHand")
    public String toHand(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        return "book/toHand";
    }
    @PatchMapping("/{id}/toHand/add")
    public String addHandler(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.toHand(id, person));
        return "redirect:/book/{id}";
    }
//    @PatchMapping("/{id}/toHand")
//    public String toHand(@ModelAttribute("book") @Valid Book book,
//                         BindingResult bindingResult, @PathVariable("id") int id, @ModelAttribute("person") Person person){
//        bookValidator.validate(book, bindingResult);
//        if(bindingResult.hasErrors())
//            return "book/show";
//
//        bookDAO.toHand(id, person);
//        return "redirect:/book/show";
//    }

    @PatchMapping("/{id}/return")
    public String returnFromHandler (Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.returnFromHandler(id));

        return "redirect:/book/{id}";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "book/edit";

        bookDAO.update(id, book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }
}
