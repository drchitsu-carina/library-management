package com.library.service.impl;

import com.library.entity.Favourite;
import com.library.repository.BookRepository;
import com.library.repository.FavouriteRepository;
import com.library.repository.UserRepository;
import com.library.service.FavouriteService;
import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public void addFavourite(Long userId, Long bookId) {

        boolean exists = favouriteRepository
                .existsByUserIdAndBookId(userId, bookId);

        if (exists) {
            return; // avoid duplicate
        }

        Favourite fav = Favourite.builder()
                .user(userRepository.findById(userId).orElseThrow())
                .book(bookRepository.findById(bookId).orElseThrow())
                .build();

        favouriteRepository.save(fav);
    }

    @Override
    public List<Favourite> getUserFavourites(Long userId) {
        return favouriteRepository.findByUserId(userId);
    }

    @Override
    public void removeFavourite(Long favId) {
        favouriteRepository.deleteById(favId);
    }

    @Override
    public Long getTotalFavourites() {
        return favouriteRepository.count(); 
    }

	@Override
	public Boolean isExist(Long userId, Long bookId) {
		// TODO Auto-generated method stub
		Optional<Favourite> favOpt=favouriteRepository.findByUserIdAndBookId(userId, bookId);
		return favOpt.isPresent();
	}

	@Override
	@Transactional
	public void removeFavourite(Long userId, Long bookId) {
	    // This one line replaces the check-and-get logic
	    favouriteRepository.deleteByUserIdAndBookId(userId, bookId);
	}
}