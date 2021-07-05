package com.example.cacheinspring;

import com.example.cacheinspring.domain.Book;
import com.example.cacheinspring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private BookService bookService;
    @Override
    public void run(String... args) throws Exception {
        int tenantId = 1;
        bookService.save(new Book(1, "Harry Potter", 1, tenantId));
        bookService.save(new Book(2, "Data Structer", 0, tenantId));
        bookService.save(new Book(3, "Clean Code", 1, 2));
//        IMap<Object, Object> map = Hazelcast.getAllHazelcastInstances().stream()
//                .findAny().orElseThrow(() -> new RuntimeException("Can not find any hazelcast instance."))
//                .getMap("books");

//        System.out.println(map.size());

//        bookService.save(new Book(1L, "Harry Potter", 1));
        while(true){
//            System.out.println(map.size());
            Thread.sleep(1000);
            System.out.println("FindAllByTenantId: 1");
            bookService.findAllByTenantId(tenantId).forEach(System.out::println);
            System.out.println("FindAllByTenantId: 2");
            bookService.findAllByTenantId(2).forEach(System.out::println);
            System.out.println("FindAllByTenantIdActive: 1");
            bookService.findAllByTenantIdActive(tenantId).forEach(System.out::println);
            System.out.println("FindAllByTenantIdAndStatus: 1, 1");
            bookService.findAllByTenantIdAndStatus(tenantId, 1).forEach(System.out::println);
            System.out.println("FindAllByTenantIdAndStatus: 1, 0");
            bookService.findAllByTenantIdAndStatus(tenantId, 0).forEach(System.out::println);
            System.out.println("Updating Book id: 1");
            bookService.save(new Book(1, "Harry Potter1", 1, tenantId));
        }
    }
}
