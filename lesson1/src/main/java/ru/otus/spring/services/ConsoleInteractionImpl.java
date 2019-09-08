package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.util.CustomMessageSource;

import java.util.Collection;
import java.util.Scanner;

@Service
public class ConsoleInteractionImpl implements IConsoleInteraction {

    Scanner scanner;

    final private CustomMessageSource message;


    @Autowired
    public ConsoleInteractionImpl(CustomMessageSource message) {

        scanner = new Scanner(System.in);
        this.message = message;
    }

    public String getInputText() {
        return scanner.next();
    }

    public Integer getInputNumber(Collection<Integer> validNumbers) {
        Integer variantNumber = null;
        try {
            variantNumber = Integer.parseInt(scanner.next());
        } catch (Exception e) {
            display(message.getMessage("message.inputNumber", null));
            variantNumber = getInputNumber(validNumbers);
        }
        if (!validNumbers.contains(variantNumber)) {
            display(message.getMessage("message.incorrectNumber", validNumbers.toArray()));
            getInputNumber(validNumbers);
        } else {
            return variantNumber;
        }

        return null;
    }

    public void display(String text) {
        System.out.println(text);
    }
}
