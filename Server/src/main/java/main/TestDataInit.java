package main;

import main.entity.User;
import main.entity.Warehouse1;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    Warehouse1Repository w1Repository;
    @Autowired
    Warehouse2Repository w2Repository;
    @Autowired
    SalesRepository salesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        userRepository.save(new User("user", pwdEncoder.encode("noparol"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", pwdEncoder.encode("parol"), Collections.singletonList("ROLE_ADMIN")));
    }
}
