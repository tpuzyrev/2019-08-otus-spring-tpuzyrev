package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.repository.GenreRepository;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findGenresByName(String name){
        if (!StringUtils.isEmpty(name)){
            return genreRepository.findByNameIgnoreCase(name);
        } else {
            return (List<Genre>) genreRepository.findAll();
        }
    }
}