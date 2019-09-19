package ru.otus.spring.usertesting.util;

import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

public interface CustomMessageSource {

    String getMessage(String var1, @Nullable Object[] var2) throws NoSuchMessageException;

    String getMessage(String var1) throws NoSuchMessageException;
}
