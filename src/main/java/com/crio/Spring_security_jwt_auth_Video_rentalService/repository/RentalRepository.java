package com.crio.Spring_security_jwt_auth_Video_rentalService.repository;

import com.crio.Spring_security_jwt_auth_Video_rentalService.model.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends MongoRepository<Rental, String> {

    // Find rentals by userId and ensure the returned date is null (active rentals)
    List<Rental> findByUserIdAndReturnedDateIsNull(String userId);

    // Find a rental record by videoId and userId where the returnedDate is null
    // (active rental)
    Optional<Rental> findByVideoIdAndUserIdAndReturnedDateIsNull(String videoId, String userId);

    // Count active rentals for a specific user
    long countByUserIdAndReturnedDateIsNull(String userId);
}
