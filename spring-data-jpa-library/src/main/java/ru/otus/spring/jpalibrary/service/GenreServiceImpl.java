package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.repository.GenreRepository;
import ru.otus.spring.jpalibrary.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Iterable<Genre> findGenresByName(String name){
        if (!StringUtils.isEmpty(name)){
            return genreRepository.findByNameIgnoreCase(name);
        } else {
            return genreRepository.findAll();
        }
    }
}