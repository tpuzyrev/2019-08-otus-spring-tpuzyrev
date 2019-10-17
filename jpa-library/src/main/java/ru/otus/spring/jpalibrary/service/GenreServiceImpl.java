package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.dao.GenreDao;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public List<Genre> findGenresByName(String name){
        if (!StringUtils.isEmpty(name)){
            return genreDao.findGenresByName(name);
        } else {
            return genreDao.findAllGenres();
        }
    }
}