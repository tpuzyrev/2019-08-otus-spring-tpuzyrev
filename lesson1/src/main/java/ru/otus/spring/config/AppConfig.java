package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.otus.spring.dao.QuestionDAO;
import ru.otus.spring.services.IQuestionDAO;
import ru.otus.spring.util.CustomResourceBundle;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    IQuestionDAO questionDAO(@Value("${fileName}") String fileName) {

        return new QuestionDAO(fileName + "_" + env.getProperty("locale") + ".csv");
    }

    @Bean
    public CustomResourceBundle messageSource() {
        CustomResourceBundle rs = new CustomResourceBundle(env.getProperty("locale"));
        rs.setBasename("localization/l10n");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(false);
        return rs;
    }
}
