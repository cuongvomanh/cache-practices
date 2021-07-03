package com.example.cacheinspring.service;

import com.example.cacheinspring.config.Contants;
import com.example.cacheinspring.domain.Book;
import com.example.cacheinspring.domain.BookRepository;
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

    @Cacheable(Contants.SERVICE_ONE_CACHE)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Cacheable(Contants.SERVICE_ONE_CACHE)
    public List<Book> findAllActive() {
        return bookRepository.findAllByStatus(ACTIVE);
    }

    @Cacheable(Contants.SERVICE_ONE_CACHE)
    public List<Book> findAllByStatus(int status) {
        return bookRepository.findAllByStatus(status);
    }
}
