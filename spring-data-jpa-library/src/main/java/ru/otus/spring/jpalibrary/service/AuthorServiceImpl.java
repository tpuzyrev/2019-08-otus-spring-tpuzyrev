package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.repository.AuthorRepository;
import ru.otus.spring.jpalibrary.domain.Author;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;

    @Override
    public List<Author> findAuthorsByBrief(String brief) {
        if (!StringUtils.isEmpty(brief)){
            return authorDao.findByNameIgnoreCase(brief);
        } else {
            return (List<Author>) authorDao.findAll();
        }
    }

    @Override
    public List<Author> getByIds(Collection<Long> ids) {
        return (List<Author>) authorDao.findAllById(ids);
    }

    @Override
    public Author getAuthor(Long id, String brief) {
        List<Author> authors = Collections.EMPTY_LIST;
        if (id != null){
            Optional<Author> authorOptional = authorDao.findById(id);
            if (authorOptional.isPresent()) {
                authors = Collections.singletonList(authorOptional.get());
            }
        } else if (!StringUtils.isEmpty(brief)) {
            authors = authorDao.findByNameIgnoreCase(brief);
        } else {
            throw new RuntimeException("Missing author param!");
        }

        if (authors.size() != 1){
            throw new RuntimeException("Could not find author by parameters: ID=" + id + ", BRIEF="+brief);
        }

        return authors.iterator().next();
    }
}
