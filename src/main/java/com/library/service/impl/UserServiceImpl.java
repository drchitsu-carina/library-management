package com.library.service.impl;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void saveOrUpdate(User user) {
        User existingUser;

        if (user.getId() != null) {
            // UPDATE MODE
            existingUser = getById(user.getId());
            
            // Check if email is being changed to one that already exists
            Optional<User> userWithEmail = userRepository.findByEmail(user.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(user.getId())) {
                throw new RuntimeException("Email already in use by another user");
            }
        } else {
            // CREATE MODE
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            existingUser = new User();
            // Default password for new users if not provided, or encode provided one
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode("123456")); // Default password
            }
        }

        // 🟢 Update standard fields
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole() != null ? user.getRole() : Role.USER);
        existingUser.setBuilding(user.getBuilding());
        existingUser.setAddress(user.getAddress());
        
        existingUser.setActive(user.isActive());

        // 🔐 Password Logic: Only update if a new password was typed in the form
        if (user.getPassword() != null && !user.getPassword().isEmpty() && !user.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: User not found");
        }
        userRepository.deleteById(id);
    }
    
    
}