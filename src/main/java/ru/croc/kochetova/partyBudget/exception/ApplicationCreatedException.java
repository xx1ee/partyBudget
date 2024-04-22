package ru.croc.kochetova.partyBudget.exception;

public class ApplicationCreatedException extends RuntimeException{
    public ApplicationCreatedException() {
        super("Заявка уже была отправлена");
    }
}
