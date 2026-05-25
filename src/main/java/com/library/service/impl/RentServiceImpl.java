package com.library.service.impl;

import com.library.entity.*;
import com.library.repository.BookRepository;
import com.library.repository.RentRepository;
import com.library.repository.UserRepository;
import com.library.service.RentService;
import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public void borrowBook(Long userId, Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getRentStatus() != RentStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rent rent = Rent.builder()
                .user(user)
                .book(book)
                .rentDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(7))
                .status(RentRecordStatus.BORROWED)
                .build();

        book.setRentStatus(RentStatus.RENTED);

        rentRepository.save(rent);
    }

    @Override
    public void returnBook(Long rentId) {

        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new RuntimeException("Rent not found"));

        if (rent.getStatus() == RentRecordStatus.RETURNED) {
            throw new RuntimeException("Already returned");
        }

        rent.setReturnDate(LocalDate.now());
        rent.setStatus(RentRecordStatus.RETURNED);

        Book book = rent.getBook();
        book.setRentStatus(RentStatus.AVAILABLE);
    }

    @Override
    public Long getActiveRentCount() {
        // This assumes you have a status in your Rent entity 
        // or you can just count all records if that's your preference
        return rentRepository.countByStatus(RentRecordStatus.BORROWED);
    }

    
 
    public void borrowBook(Long userId, Long bookId, LocalDate rentDate, LocalDate dueDate) {
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        Rent rent = Rent.builder()
                .user(user)
                .book(book)
                .rentDate(rentDate)
                .dueDate(dueDate)
                .status(RentRecordStatus.BORROWED)
                .build();
        

        rentRepository.save(rent);
        
        book.setRentStatus(RentStatus.RENTED);
        bookRepository.save(book);
    }

    @Override
    public Collection<Rent> getRentsByUserId(Long id) {
        // We only want to show books that are currently BORROWED
        // This acts as the "To-Return" or "To-Do" list
        return rentRepository.findByUserIdAndStatusOrderByRentDateDesc(id, RentRecordStatus.BORROWED);
    }

    @Override
    public String lastBorrowedBook(Long id) {

        List<Rent> rents = rentRepository.findLatestRentByUser(id);

        if (rents == null || rents.isEmpty()) {
            return "No borrowing history";
        }

        Rent latest = rents.get(0);

        return latest.getBook().getTitle()
                + " (Due: " + latest.getDueDate() + ")";
    }
}