package ru.otus.spring.usertesting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.usertesting.dao.QuestionDAOImpl;
import ru.otus.spring.usertesting.dao.QuestionDAO;
import ru.otus.spring.usertesting.util.CustomResourceBundle;

@Configuration
public class AppConfig {

    @Autowired
    AppProperties properties;

    @Bean
    QuestionDAO questionDAO() {
        return new QuestionDAOImpl(properties.getFileLocaleName());
    }

    @Bean
    public CustomResourceBundle messageSource() {
        CustomResourceBundle rs = new CustomResourceBundle(properties.getLocale());
        rs.setBasename("localization/l10n");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(false);
        return rs;
    }
}
