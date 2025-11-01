package by.antonpaulavets.paymentsystem.dto;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private List<CardResponseDTO> cards;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String surname, LocalDate birthDate, String email, List<CardResponseDTO> cards) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CardResponseDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardResponseDTO> cards) {
        this.cards = cards;
    }
}
