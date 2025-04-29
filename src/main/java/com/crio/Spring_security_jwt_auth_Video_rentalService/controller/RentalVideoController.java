package com.crio.Spring_security_jwt_auth_Video_rentalService.controller;

import com.crio.Spring_security_jwt_auth_Video_rentalService.model.Video;
import com.crio.Spring_security_jwt_auth_Video_rentalService.exceptions.RentalLimitExceededException;
import com.crio.Spring_security_jwt_auth_Video_rentalService.exceptions.VideoNotFoundException;
import com.crio.Spring_security_jwt_auth_Video_rentalService.model.Rental;
import com.crio.Spring_security_jwt_auth_Video_rentalService.service.RentalService;
import com.crio.Spring_security_jwt_auth_Video_rentalService.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class RentalVideoController {

    private final VideoRepository videoRepository;
    private final RentalService rentalService;

    // Public endpoint: anyone can view available videos
    @GetMapping("/available")
    public ResponseEntity<List<Video>> getAvailableVideos() {
        List<Video> videos = videoRepository.findByAvailableTrue();
        return ResponseEntity.ok(videos);
    }

    // ADMIN only: Add a new video
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Video> addVideo(@RequestBody Video video) {
        System.out.println("heere " + video);
        video.setAvailable(true); // New videos are available by default
        Video savedVideo = videoRepository.save(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
    }

    // ADMIN only: Update a video
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Video> updateVideo(@PathVariable String id, @RequestBody Video updatedVideo) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isEmpty()) {
            throw new VideoNotFoundException("Video with ID " + id + " not found.");
        }

        Video existingVideo = video.get();
        existingVideo.setTitle(updatedVideo.getTitle());
        existingVideo.setDirector(updatedVideo.getDirector());
        existingVideo.setGenre(updatedVideo.getGenre());
        existingVideo.setAvailable(updatedVideo.isAvailable());

        videoRepository.save(existingVideo);
        return ResponseEntity.ok(existingVideo);
    }

    // ADMIN only: Delete a video
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteVideo(@PathVariable String id) {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException("Video with ID " + id + " not found.");
        }

        videoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Video deleted successfully.");
    }

    // CUSTOMER: Rent a video
    @PostMapping("/{videoId}/rent")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Rental> rentVideo(@PathVariable String videoId, @RequestParam String userId) {
        try {
            Rental rental = rentalService.rentVideo(videoId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(rental);
        } catch (RentalLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Rental());
        }
    }

    // CUSTOMER: Return a video
    @PostMapping("/{videoId}/return")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> returnVideo(@PathVariable String videoId, @RequestParam String userId) {
        try {
            rentalService.returnVideo(videoId, userId);
            return ResponseEntity.ok("Video returned successfully.");
        } catch (VideoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
        }
    }

    // CUSTOMER: View all rentals
    @GetMapping("/my-rentals")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<Rental>> viewMyRentals(@RequestParam String userId) {
        List<Rental> rentals = rentalService.getRentalsByUser(userId);
        return ResponseEntity.ok(rentals);
    }
}
