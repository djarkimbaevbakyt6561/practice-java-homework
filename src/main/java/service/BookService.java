package service;

import model.Book;

import java.util.List;

public interface BookService {
    String createBookTable();
    boolean saveBook(Book book);
    List<Book> findAllAuthorBookById(Long authorId);
    String updateBookById(Long id, Book book);
    Book findBookById(Long id);
    void deleteBookById(Long id);
    List<Book> findAllBooksByAuthorCountry(String authorCountry);
    void dropBookTable();
    boolean cleanTable();
    List<Book> sortByPublishedYear(String ascOrDesc);
    String updateAuthorsAllBooksPrice(Long authorId, int newBookPrice);

}
