package com.crio.Spring_security_jwt_auth_Video_rentalService.service;

import com.crio.Spring_security_jwt_auth_Video_rentalService.model.Video;
import com.crio.Spring_security_jwt_auth_Video_rentalService.model.Rental;
import com.crio.Spring_security_jwt_auth_Video_rentalService.repository.RentalRepository;
import com.crio.Spring_security_jwt_auth_Video_rentalService.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final VideoRepository videoRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(VideoRepository videoRepository, RentalRepository rentalRepository) {
        this.videoRepository = videoRepository;
        this.rentalRepository = rentalRepository;
    }

    // Rent a video
    public Rental rentVideo(String videoId, String userId) {
        // Check if user has already rented 2 videos
        long activeRentals = rentalRepository.countByUserIdAndReturnedDateIsNull(userId);
        if (activeRentals >= 2) {
            throw new IllegalStateException("Cannot rent more than 2 videos at a time.");
        }

        // Find the video
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isEmpty()) {
            throw new IllegalArgumentException("Video not found");
        }

        Video video = videoOpt.get();
        if (!video.isAvailable()) {
            throw new IllegalStateException("Video is not available for rent");
        }

        // Mark the video as rented (unavailable)
        video.setAvailable(false);
        videoRepository.save(video);

        // Create a rental record
        Rental rental = new Rental();
        rental.setVideoId(videoId);
        rental.setUserId(userId);
        rental.setRentedDate(LocalDate.now());

        // Save the rental record
        return rentalRepository.save(rental);
    }

    // Return a rented video
    public void returnVideo(String videoId, String userId) {
        // Find the rental record for this video and user
        Optional<Rental> rentalOpt = rentalRepository.findByVideoIdAndUserIdAndReturnedDateIsNull(videoId, userId);
        if (rentalOpt.isEmpty()) {
            throw new IllegalStateException("Rental record not found or video already returned");
        }

        Rental rental = rentalOpt.get();

        // Mark the rental as returned
        rental.setReturnedDate(LocalDate.now());
        rentalRepository.save(rental);

        // Mark the video as available
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();
            video.setAvailable(true);
            videoRepository.save(video);
        }
    }

    // Get all rentals for a user
    public List<Rental> getRentalsByUser(String userId) {
        return rentalRepository.findByUserIdAndReturnedDateIsNull(userId);
    }
}
