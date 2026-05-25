package com.library.config;

import com.library.entity.*;
import com.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) return;

        // 👤 USERS
        User admin = User.builder()
                .name("Admin")
                .email("admin@library.com")
                .phone("09123456789")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .active(true)
                .build();

        User user = User.builder()
                .name("User One")
                .email("user@library.com")
                .phone("09998887777")
                .password(passwordEncoder.encode("user123"))
                .role(Role.USER)
                .active(true)
                .build();

        userRepository.save(admin);
        userRepository.save(user);

        // 📂 CATEGORIES
        Category it = Category.builder().name("IT").build();
        Category science = Category.builder().name("Science").build();
        Category literature = Category.builder().name("Literature").build();

        categoryRepository.save(it);
        categoryRepository.save(science);
        categoryRepository.save(literature);

        // 📚 BOOKS
        Book b1 = Book.builder()
                .title("Java Basics")
                .author("James Gosling")
                .publishedYear(2020)
                .edition("1st")
                .publisher("Oracle Press")
                .accessionNo("ACC-001")
                .shelfNo("S1")
                .building("A Block")   // ✅ FIXED
                .image("java.jpg")
                .rentStatus(RentStatus.AVAILABLE)
                .category(it)
                .build();

        Book b2 = Book.builder()
                .title("Spring Boot Guide")
                .author("Craig Walls")
                .publishedYear(2021)
                .edition("2nd")
                .publisher("Manning")
                .accessionNo("ACC-002")
                .shelfNo("S1")
                .building("A Block")   // ✅ FIXED
                .image("spring.jpg")
                .rentStatus(RentStatus.AVAILABLE)
                .category(it)
                .build();

        Book b3 = Book.builder()
                .title("Physics World")
                .author("Einstein")
                .publishedYear(2018)
                .edition("3rd")
                .publisher("Science Pub")
                .accessionNo("ACC-003")
                .shelfNo("S2")
                .building("B Block")
                .image("physics.jpg")
                .rentStatus(RentStatus.AVAILABLE)
                .category(science)
                .build();

        Book b4 = Book.builder()
                .title("English Classics")
                .author("Shakespeare")
                .publishedYear(2015)
                .edition("1st")
                .publisher("Oxford")
                .accessionNo("ACC-004")
                .shelfNo("S3")
                .building("C Block")
                .image("literature.jpg")
                .rentStatus(RentStatus.AVAILABLE)
                .category(literature)
                .build();

        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);
        bookRepository.save(b4);

        System.out.println("✅ Sample data inserted successfully!");
    }
}