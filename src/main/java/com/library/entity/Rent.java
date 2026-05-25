package com.library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rent_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Ensures compatibility with HTML date input
    private LocalDate rentDate;

    @Column(name = "due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    
    @Column(name = "return_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate; 

    @Enumerated(EnumType.STRING)
    private RentRecordStatus status; // BORROWED, RETURNED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
}