package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    public static final int ACTIVE = 1;
    @Autowired
    private BookRepository bookRepository;

    public void save(Book book) {
        bookRepository.save(book);
    }

    @Cacheable(Contants.CACHE_PREFIX)
//    @Cacheable(key="books")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Cacheable(Contants.CACHE_PREFIX)
//    @Cacheable(key="activebooks")
    public List<Book> findAllActive() {
        return bookRepository.findAllByStatus(ACTIVE);
    }

    @Cacheable(Contants.CACHE_PREFIX)
//    @Cacheable(key="booksbystatus")
    public List<Book> findAllByStatus(int status) {
        return bookRepository.findAllByStatus(status);
    }
}
