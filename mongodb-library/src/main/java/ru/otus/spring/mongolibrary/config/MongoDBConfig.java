package ru.otus.spring.mongolibrary.config;

import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.mongolibrary.listener.BookCascadeSaveMongoEventListener;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring.mongolibrary.repository")
public class MongoDBConfig {

    @Bean
    public SpringBootMongock mongock(ApplicationContext springContext, MongoClient mongoClient) {
        return new SpringBootMongockBuilder(mongoClient, "test", "ru.otus.spring.mongolibrary.changesets")
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }

    @Bean
    public BookCascadeSaveMongoEventListener userCascadingMongoEventListener() {
        return new BookCascadeSaveMongoEventListener();
    }
}
