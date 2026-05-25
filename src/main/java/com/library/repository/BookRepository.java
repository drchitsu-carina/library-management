package com.library.repository;

import com.library.entity.Book;
import com.library.entity.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByRentStatus(RentStatus status);
}