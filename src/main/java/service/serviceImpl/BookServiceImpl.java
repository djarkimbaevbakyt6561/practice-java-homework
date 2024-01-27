package service.serviceImpl;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import model.Book;
import service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public String createBookTable() {
        return bookDao.createBookTable();
    }

    @Override
    public boolean saveBook(Book book) {
        return bookDao.saveBook(book);
    }

    @Override
    public List<Book> findAllAuthorBookById(Long authorId) {
        return bookDao.findAllAuthorBookById(authorId);
    }

    @Override
    public String updateBookById(Long id, Book book) {
        return bookDao.updateBookById(id,book);
    }

    @Override
    public Book findBookById(Long id) {
        return bookDao.findBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
      bookDao.deleteBookById(id);
    }

    @Override
    public List<Book> findAllBooksByAuthorCountry(String authorCountry) {
        return bookDao.findAllBooksByAuthorCountry(authorCountry);
    }

    @Override
    public void dropBookTable() {
        bookDao.dropBookTable();
    }

    @Override
    public boolean cleanTable() {
        return bookDao.cleanTable();
    }

    @Override
    public List<Book> sortByPublishedYear(String ascOrDesc) {
        return bookDao.sortByPublishedYear(ascOrDesc);
    }

    @Override
    public String updateAuthorsAllBooksPrice(Long authorId, int newBookPrice) {
        return bookDao.updateAuthorsAllBooksPrice(authorId, newBookPrice);
    }
}
