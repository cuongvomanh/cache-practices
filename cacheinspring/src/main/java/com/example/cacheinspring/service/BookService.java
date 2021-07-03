package com.example.cacheinspring.service;

import com.example.cacheinspring.config.Contants;
import com.example.cacheinspring.config.MySpringProperties;
import com.example.cacheinspring.domain.Book;
import com.example.cacheinspring.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
//    @Cacheable("books")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Cacheable(Contants.CACHE_PREFIX)
//    @Cacheable("activebooks")
    public List<Book> findAllActive() {
        return bookRepository.findAllByStatus(ACTIVE);
    }

    @Cacheable(Contants.CACHE_PREFIX)
//    @Cacheable("booksbystatus")
    public List<Book> findAllByStatus(int status) {
        return bookRepository.findAllByStatus(status);
    }
}
