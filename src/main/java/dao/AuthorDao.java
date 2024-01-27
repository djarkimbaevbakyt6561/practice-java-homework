package dao;


import model.Author;

import java.util.List;

public interface AuthorDao {

    // crud
    boolean createAuthorTable();
    String saveAuthor(Author newAuthor);
    String updateAuthorById(Long id, Author author);
    Author findAuthorById(Long id);
    void deleteAuthorById(Long id);
    List<Author> findAllAuthors();
    void dropAuthorTable();
    boolean cleanTable();
    List<Author> sortByDateOfBirth();
}
