package ru.otus.spring.jdbclibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.jdbclibrary.dao.GenreDao;
import ru.otus.spring.jdbclibrary.dto.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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