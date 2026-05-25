package com.library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.annotation.CreatedDate;

@Entity
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;
    
    private String building;
    
    private String address;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // relationships
    @OneToMany(mappedBy = "user")
    private List<Rent> rents;

    @OneToMany(mappedBy = "user")
    private List<Favourite> favourites;
}