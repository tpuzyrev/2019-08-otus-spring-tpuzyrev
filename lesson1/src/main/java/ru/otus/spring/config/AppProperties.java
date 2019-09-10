package ru.otus.spring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@Data
public class AppProperties {

    @Value("${locale}")
    public String locale;
    @Value("${fileName}")
    public String fileName;

    public String getFileName() {
        return this.fileName + "_" + this.locale + ".csv";
    }
}
