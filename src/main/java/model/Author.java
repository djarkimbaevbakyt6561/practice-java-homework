package model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private LocalDate dateOfBirth;

    public Author(String firstName, String lastName, String email, String country, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
    }
}
