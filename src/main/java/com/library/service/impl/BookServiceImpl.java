package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> search(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book saveBookWithImage(Book book, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                // 1. Create filename: title_milliseconds.extension
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = book.getTitle().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + extension;

                // 2. Define Path (Ensure this folder exists in your project)
                Path uploadPath = Paths.get("src/main/resources/static/images/books/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 3. Save File
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
               
                // 4. Set path in DB
                book.setImage("/images/books/" + fileName);
                
            } catch (IOException e) {
                throw new RuntimeException("Could not save image file", e);
            }
        }
        return bookRepository.save(book);
    }
    
    @Override
    public Book saveOrUpdate(Book book, MultipartFile file, Long categoryId) {

        Book existingBook = null;

        // 🔥 UPDATE MODE
        if (book.getId() != null) {
            existingBook = bookRepository.findById(book.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
        } else {
            existingBook = new Book();
        }

        // 🟢 Copy fields
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublishedYear(book.getPublishedYear());
        existingBook.setEdition(book.getEdition());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setAccessionNo(book.getAccessionNo());
        existingBook.setShelfNo(book.getShelfNo());
        existingBook.setBuilding(book.getBuilding());
        existingBook.setRentStatus(book.getRentStatus());

        // 🟡 Category
        if (categoryId != null) {
            Category category = new Category();
            category.setId(categoryId);
            existingBook.setCategory(category);
        }

        // 🟣 IMAGE HANDLING
        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

                String fileName = book.getTitle().replaceAll("\\s+", "_")
                        + "_" + System.currentTimeMillis()
                        + extension;

                Path uploadPath = Paths.get("src/main/resources/static/images/books/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                existingBook.setImage("/images/books/" + fileName);

            } catch (Exception e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }else {
        	// If no new image is uploaded and the book currently has no image, set default
            if (existingBook.getImage() == null || existingBook.getImage().isEmpty()) {
                existingBook.setImage("/images/books/no-cover.jpg");
            }
        }

        return bookRepository.save(existingBook);
    }
}