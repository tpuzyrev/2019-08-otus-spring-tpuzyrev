package ru.otus.spring.util;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class CustomResourceBundle extends ResourceBundleMessageSource implements CustomMessageSource {

    private Locale locale;

    public CustomResourceBundle(String locale) {
        this.locale = new Locale(locale);
    }

    @Override
    public String getMessage(String var1, Object[] var2) throws NoSuchMessageException {
        return getMessage(var1, var2, locale);
    }
}
