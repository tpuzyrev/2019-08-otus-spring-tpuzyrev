package ru.otus.spring.services;

import java.util.Collection;
import java.util.List;

public interface UserInteraction {

    String getInputText();

    List<Integer> getInputNumber(Collection<Integer> validNumbers, boolean hasSeveralVariant);

    void display(String text);
}
