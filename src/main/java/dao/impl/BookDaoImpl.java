package dao.impl;

import config.JdbcConfig;
import dao.BookDao;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public String createBookTable() {
        String query = "create table if not exists books(" +
                "id serial primary key," +
                "name varchar," +
                "country varchar," +
                "published_year int," +
                "price int," +
                "author_id int references authors(id));";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return "Book table is created!";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Failed";
    }

    @Override
    public boolean saveBook(Book book) {
        String query = """
                insert into books(name, country, published_year, price, author_id)
                values (?, ?, ?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getCountry());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.setInt(4, book.getPrice());
            preparedStatement.setLong(5, book.getAuthor());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Book> findAllAuthorBookById(Long authorId) {

        List<Book> books = new ArrayList<>();
        String query = "select * from books b inner join authors a on b.author_id = a.id where b.author_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("country"),
                                resultSet.getInt("published_year"),
                                resultSet.getInt("price"),
                                resultSet.getLong("author_id")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public String updateBookById(Long id, Book book) {
        String query = "update books " +
                "set name = ?," +
                "country = ?," +
                "published_year = ?," +
                "price = ?," +
                "author_id = ?" +
                "where id = ?";
        int execute = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getCountry());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.setInt(4, book.getPrice());
            preparedStatement.setLong(5, book.getAuthor());
            preparedStatement.setLong(6, id);
            execute = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return execute != 0 ? "Updated" : "Failed";
    }

    @Override
    public Book findBookById(Long id) {
        String query = "select * from books where id = ?";
        Book book = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book();
                    book.setId(resultSet.getLong("id"));
                    book.setCountry(resultSet.getString("country"));
                    book.setName(resultSet.getString("name"));
                    book.setPrice(resultSet.getInt("price"));
                    book.setPublishedYear(resultSet.getInt("published_year"));
                    book.setAuthor(resultSet.getLong("author_id"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        String query = "delete from books where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Book> findAllBooksByAuthorCountry(String authorCountry) {
        String query = """
                select * from books b inner join authors a on b.author_id = a.id
                where a.country = ?
                """;

        List<Book> books = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, authorCountry);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("country"),
                                resultSet.getInt("published_year"),
                                resultSet.getInt("price"),
                                resultSet.getLong("author_id")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public void dropBookTable() {
        String query = "drop table books;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean cleanTable() {
        String query = "delete from books";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Book> sortByPublishedYear(String ascOrDesc) {
        List<Book> books = new ArrayList<>();
        String query = "select * from authors order by published_year ?;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                books.add(new Book(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("country"),
                                resultSet.getInt("published_year"),
                                resultSet.getInt("price"),
                                resultSet.getLong("author_id")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public String updateAuthorsAllBooksPrice(Long authorId, int newBookPrice) {
        String query = """
                update books
                set price = ?
                where author_id = ?
                """;
        int execute = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newBookPrice);
            preparedStatement.setLong(2, authorId);
            execute = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return execute != 0 ? "Updated" : "Failed";
    }
}
