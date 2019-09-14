package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.MessageKey;
import ru.otus.spring.util.CustomMessageSource;

import java.util.*;

@Service
public class ConsoleUserInteractionImpl implements UserInteraction {

    private Scanner scanner;

    final private CustomMessageSource message;


    @Autowired
    public ConsoleUserInteractionImpl(CustomMessageSource message) {

        scanner = new Scanner(System.in);
        this.message = message;
    }

    public String getInputText() {
        return scanner.next();
    }

    public List<Integer> getInputNumber(Collection<Integer> validNumbers, boolean hasSeveralVariant) {
        final List<Integer> variantNumberList = new ArrayList<>();
        Optional<String> inputOpt = Optional.of(scanner.next());
        if (hasSeveralVariant && inputOpt.get().contains(",")) {
            for (String s1 : inputOpt.get().split(",")) {
                try {
                    Integer variantNumber = Integer.parseInt(s1);
                    if (!validNumbers.contains(variantNumber)) {
                        display(message.getMessage(MessageKey.INCORRECT_NUMBER, new Object[] {Arrays.toString(validNumbers.toArray())}));
                        return getInputNumber(validNumbers, hasSeveralVariant);
                    }
                    variantNumberList.add(variantNumber);
                } catch (Exception e) {
                    display(message.getMessage(MessageKey.INPUT_NUMBER));
                    return getInputNumber(validNumbers, hasSeveralVariant);
                }
            }
        } else {
            try {
                Integer variantNumber = Integer.parseInt(inputOpt.get());
                if (!validNumbers.contains(variantNumber)) {
                    display(message.getMessage(MessageKey.INCORRECT_NUMBER, new Object[] {Arrays.toString(validNumbers.toArray())}));
                    return getInputNumber(validNumbers, hasSeveralVariant);
                }
                variantNumberList.add(variantNumber);
            } catch (Exception e) {
                display(message.getMessage(MessageKey.INPUT_NUMBER));
                return getInputNumber(validNumbers, hasSeveralVariant);
            }
        }
        return variantNumberList;
    }

    public void display(String text) {
        System.out.println(text);
    }
}
