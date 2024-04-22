package ru.croc.kochetova.partyBudget.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
