package dao.impl;

import config.JdbcConfig;
import dao.AuthorDao;
import model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {

    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public boolean createAuthorTable() {
        int execute = 0;
        String query = "create table if not exists authors(" +
                "id serial primary key," +
                "first_name varchar," +
                "last_name varchar," +
                "email varchar," +
                "country varchar," +
                "date_of_birth date);";

        try (Statement statement = connection.createStatement()) {
            execute = statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return execute == 0;
    }

    @Override
    public String saveAuthor(Author newAuthor) {
        String query = """
                INSERT INTO authors(first_name, last_name, country, email, date_of_birth)
                VALUES (?, ?, ?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newAuthor.getFirstName());
            preparedStatement.setString(2, newAuthor.getLastName());
            preparedStatement.setString(3, newAuthor.getCountry());
            preparedStatement.setString(4, newAuthor.getEmail());
            preparedStatement.setDate(5, Date.valueOf(newAuthor.getDateOfBirth()));
            preparedStatement.executeUpdate();
            return "Author successfully added!";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Author didnt add!";
    }

    @Override
    public String updateAuthorById(Long id, Author author) {
        String query = "update authors " +
                "set first_name = ?," +
                "last_name = ?," +
                "email = ?," +
                "country = ?," +
                "date_of_birth = ? " +
                "where id = ?";
        int execute = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setString(3, author.getEmail());
            preparedStatement.setString(4, author.getCountry());
            preparedStatement.setDate(5, Date.valueOf(author.getDateOfBirth()));
            preparedStatement.setLong(6, id);
            execute = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return execute != 0 ? "Updated" : "Failed";
    }

    @Override
    public Author findAuthorById(Long id) {
        String query = "SELECT * FROM authors WHERE id = ?";
        Author author = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    author = new Author();
                    author.setId(resultSet.getLong("id"));
                    author.setCountry(resultSet.getString("country"));
                    author.setEmail(resultSet.getString("email"));
                    author.setLastName(resultSet.getString("last_name"));
                    author.setFirstName(resultSet.getString("first_name"));
                    author.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        String query = "delete from authors where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Author> findAllAuthors() {

        List<Author> authors = new ArrayList<>();

        String query = "select * from authors;";
        return getAuthors(authors, query);
    }

    @Override
    public void dropAuthorTable() {
        String query = "drop table authors;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean cleanTable() {
        String query = "delete from authors";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Author> sortByDateOfBirth() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM authors ORDER BY date_of_birth;";
        return getAuthors(authors, query);
    }

    private List<Author> getAuthors(List<Author> authors, String query) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));
                author.setEmail(resultSet.getString("email"));
                author.setCountry(resultSet.getString("country"));
                author.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return authors;
    }

}
