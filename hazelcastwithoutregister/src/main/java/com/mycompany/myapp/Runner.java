package com.mycompany.myapp;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

@Repository
public class Runner implements CommandLineRunner {
    private final UserRepository userRepository;

    public Runner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setId("cuongvm12");
        user.setFirstName("cuongvomanh");
        user.setLogin("cuongvm12");
        userRepository.save(user);
        while (true){
            Thread.sleep(1000);
            userRepository.findById("cuongvm12").ifPresent(System.out::println);
        }

    }
}
