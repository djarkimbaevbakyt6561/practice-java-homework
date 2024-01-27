import model.Author;
import model.Book;
import service.AuthorService;
import service.BookService;
import service.serviceImpl.AuthorServiceImpl;
import service.serviceImpl.BookServiceImpl;

import java.time.LocalDate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AuthorService authorService = new AuthorServiceImpl();
        BookService bookService  = new BookServiceImpl();

//        System.out.println(authorService.createAuthorTable());
//        System.out.println(bookService.createBookTable());

//        System.out.println(authorService.saveAuthor( new Author("Nurlan", "LastName", "nurlan@gmail.com", "kg", LocalDate.of(2007, 12, 12))));
//        System.out.println(bookService.saveBook(new Book("Hunter", "Germany", 1994, 20000, 1L ) ));
//        System.out.println(authorService.findAllAuthors());
//        System.out.println(authorService.findAuthorById(1L));
//        System.out.println(authorService.sortByDateOfBirth());
//        System.out.println(authorService.updateAuthorById(1L, new Author("Baha", "Dhzarkymbave", "baha@gmail.com", "FUckyou", LocalDate.of(3444, 1, 23))));
//        System.out.println(bookService.findAllAuthorBookById(1L));
    }
}