package ru.croc.kochetova.partyBudget.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "receiving_money_application")
public class ReceivingMoneyApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Positive(message = "Сумма должна быть больше 0")
    @NotNull(message = "Сумма не должна быть пустой")
    BigDecimal amount;
    @NotBlank(message = "Цель не должна быть пустой")
    String target;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    public User user;
    @Enumerated(EnumType.STRING)
    Status status;

    public ReceivingMoneyApplication(BigDecimal amount, String target, User user) {
        this.amount = amount;
        this.target = target;
        this.user = user;
    }

    public ReceivingMoneyApplication() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
