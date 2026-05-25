package com.library.repository;

import com.library.entity.Rent;
import com.library.entity.RentRecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findByUserId(Long userId);

    List<Rent> findByStatus(RentRecordStatus status);

    Long countByStatus(RentRecordStatus status);
	
    // This is the correct version for your "To-Return" list
    // It filters by User ID AND Status, then sorts by date
    Collection<Rent> findByUserIdAndStatusOrderByRentDateDesc(Long userId, RentRecordStatus status);

	List<Rent> findLatestRentByUser(Long id);

}