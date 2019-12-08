package ru.otus.spring.jpalibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring.jpalibrary.repository")
public class MongoDBConfig {
}
