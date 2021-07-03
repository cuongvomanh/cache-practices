package com.mycompany.myapp;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private BookService bookService;
    @Override
    public void run(String... args) throws Exception {
        bookService.save(new Book(1, "Harry Potter", 1));
        bookService.save(new Book(2, "Data Structer", 0));
//        IMap<Object, Object> map = Hazelcast.getAllHazelcastInstances().stream()
//                .findAny().orElseThrow(() -> new RuntimeException("Can not find any hazelcast instance."))
//                .getMap("books");

//        System.out.println(map.size());

//        bookService.save(new Book(1L, "Harry Potter", 1));
        while(true){
//            System.out.println(map.size());
            Thread.sleep(1000);
            System.out.println("All: ");
            bookService.findAll().forEach(System.out::println);
            System.out.println("FindAllByStatus: ");
            bookService.findAllActive().forEach(System.out::println);
            System.out.println("FindAllByStatus: ");
            bookService.findAllByStatus(1).forEach(System.out::println);
            System.out.println("FindAllByStatus: 0");
            bookService.findAllByStatus(0).forEach(System.out::println);
        }
    }
}
