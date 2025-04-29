package com.crio.Spring_security_jwt_auth_Video_rentalService.repository;

import com.crio.Spring_security_jwt_auth_Video_rentalService.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface VideoRepository extends MongoRepository<Video, String> {

    // Find videos by availability (only available videos)
    List<Video> findByAvailableTrue();

    // Find a video by its ID
    Optional<Video> findById(String id);

    // Count videos based on their availability (for rental management)
    long countByAvailableTrue();
}
