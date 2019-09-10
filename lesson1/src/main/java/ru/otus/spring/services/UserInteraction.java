package ru.otus.spring.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserInteraction {

    String getInputText();

    Optional<List<Integer>> getInputNumber(Collection<Integer> validNumbers, boolean hasSeveralVariant);

    void display(String text);
}
