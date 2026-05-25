package com.library.service;

import java.time.LocalDate;
import java.util.Collection;

import org.jspecify.annotations.Nullable;

import com.library.entity.Rent;

public interface RentService {

    void borrowBook(Long userId, Long bookId);

    void returnBook(Long rentId);

	@Nullable
	Object getActiveRentCount();

	void borrowBook(Long userId, Long bookId, LocalDate rentDate, LocalDate dueDate);

	Collection<Rent> getRentsByUserId(Long id);

	String lastBorrowedBook(Long id);
}