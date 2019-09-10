package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.util.CustomMessageSource;

import java.util.*;

@Service
public class ConsoleInteractionImpl implements UserInteraction {

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

    public Optional<List<Integer>> getInputNumber(Collection<Integer> validNumbers, boolean hasSeveralVariant) {
        final List<Integer> variantNumberList = new ArrayList<>();
        Optional<String> inputOpt = Optional.of(scanner.next());
        if (hasSeveralVariant && inputOpt.get().contains(",")) {
            for (String s1 : inputOpt.get().split(",")) {
                try {
                    Integer variantNumber = Integer.parseInt(s1);
                    if (!validNumbers.contains(variantNumber)) {
                        display(message.getMessage("message.incorrectNumber", new Object[] {Arrays.toString(validNumbers.toArray())}));
                        return getInputNumber(validNumbers, hasSeveralVariant);
                    }
                    variantNumberList.add(variantNumber);
                } catch (Exception e) {
                    display(message.getMessage("message.inputNumber"));
                    return getInputNumber(validNumbers, hasSeveralVariant);
                }
            }
        } else {
            try {
                Integer variantNumber = Integer.parseInt(inputOpt.get());
                if (!validNumbers.contains(variantNumber)) {
                    display(message.getMessage("message.incorrectNumber", new Object[] {Arrays.toString(validNumbers.toArray())}));
                    return getInputNumber(validNumbers, hasSeveralVariant);
                }
                variantNumberList.add(variantNumber);
            } catch (Exception e) {
                display(message.getMessage("message.inputNumber"));
                return getInputNumber(validNumbers, hasSeveralVariant);
            }
        }
        return Optional.of(variantNumberList);
    }

    public void display(String text) {
        System.out.println(text);
    }
}
