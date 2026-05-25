package com.library.service;

import com.library.entity.Book;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    List<Book> getAllBooks();

    List<Book> search(String keyword);

    Book getById(Long id);

    Book save(Book book);

    void delete(Long id);

	Book saveBookWithImage(Book book, MultipartFile file);

	Book saveOrUpdate(Book book, MultipartFile file, Long categoryId);
}