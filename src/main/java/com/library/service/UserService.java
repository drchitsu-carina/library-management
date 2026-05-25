package com.library.service;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;

import com.library.entity.User;

public interface UserService {

    User register(User user);

    Optional<User> findByEmail(String email);

	List<User> getAllUsers();

	void saveOrUpdate(User user);

	void delete(Long id);

	User getById(Long id);
}