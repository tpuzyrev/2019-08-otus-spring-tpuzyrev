package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.dao.QuestionDAOImpl;
import ru.otus.spring.services.QuestionDAO;
import ru.otus.spring.util.CustomResourceBundle;

@Configuration
public class AppConfig {

    @Autowired
    AppProperties properties;

    @Bean
    QuestionDAO questionDAO() {
        return new QuestionDAOImpl(properties.getFileName());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
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
