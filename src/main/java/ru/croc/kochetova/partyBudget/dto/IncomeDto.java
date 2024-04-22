package ru.croc.kochetova.partyBudget.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class IncomeDto {
    @Positive(message = "Сумма должна быть больше 0")
    @NotNull(message = "Сумма не должна быть пустой")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal money) {
        this.amount = money;
    }
}
