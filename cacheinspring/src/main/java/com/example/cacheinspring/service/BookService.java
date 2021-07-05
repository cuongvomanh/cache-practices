package com.example.cacheinspring.service;

import com.example.cacheinspring.config.Contants;
import com.example.cacheinspring.domain.Book;
import com.example.cacheinspring.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    public static final int ACTIVE = 1;
    @Autowired
    private BookRepository bookRepository;

    @Caching( evict = {@CacheEvict(value = Contants.SERVICE_ONE_CACHE, key = "'bookbytenant:' + #book.getTenantId()"),
            @CacheEvict(value = Contants.SERVICE_ONE_CACHE, key = "'bookbytenantactive:' + #book.getTenantId()"),
            @CacheEvict(value = Contants.SERVICE_ONE_CACHE, key = "'bookbytenantandstatus:' + #book.getTenantId() + ':' + #book.getStatus()")}
    )

    public void save(Book book) {
        save(book, book.getTenantId());
    }

    public void save(Book book, int tenantId){
        bookRepository.save(book);
    }

    @Cacheable(value = Contants.SERVICE_ONE_CACHE, key = "'bookbytenant:' + #tenantId")
    public List<Book> findAllByTenantId(int tenantId) {
        return bookRepository.findAllByTenantId(tenantId);
    }

    @Cacheable(value = Contants.SERVICE_ONE_CACHE, key = "'bookbytenantactive:' + #tenantId")
    public List<Book> findAllByTenantIdActive(int tenantId) {
        return bookRepository.findAllByTenantIdAndStatus(tenantId, ACTIVE);
    }

    @Cacheable(value = Contants.SERVICE_ONE_CACHE, key = "'bookbytenantandstatus:' + #tenantId + ':' + #status")
    public List<Book> findAllByTenantIdAndStatus(int tenantId, int status) {
        return bookRepository.findAllByTenantIdAndStatus(tenantId, status);
    }
}
