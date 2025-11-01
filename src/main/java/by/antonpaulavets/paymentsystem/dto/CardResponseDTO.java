package by.antonpaulavets.paymentsystem.dto;

import java.time.LocalDate;


public class CardResponseDTO {
    private Long id;
    private Long userId;
    private String number;
    private String holder;
    private LocalDate expirationDate;

    public CardResponseDTO() {
    }

    public CardResponseDTO(Long id, Long userId, String number, String holder, LocalDate expirationDate) {
        this.id = id;
        this.userId = userId;
        this.number = number;
        this.holder = holder;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
