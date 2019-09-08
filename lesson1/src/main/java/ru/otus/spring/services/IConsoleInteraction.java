package ru.otus.spring.services;

import java.util.Collection;

public interface IConsoleInteraction {

    String getInputText();

    Integer getInputNumber(Collection<Integer> validNumbers);

    void display(String text);
}
