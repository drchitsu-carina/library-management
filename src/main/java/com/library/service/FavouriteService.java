package com.library.service;

import com.library.entity.Favourite;

import java.util.List;

import org.jspecify.annotations.Nullable;

public interface FavouriteService {

    void addFavourite(Long userId, Long bookId);

    List<Favourite> getUserFavourites(Long userId);
    
    Boolean isExist(Long userId,Long bookId);

    void removeFavourite(Long favId);

	@Nullable
	Object getTotalFavourites();

	void removeFavourite(Long userId, Long bookId);
}