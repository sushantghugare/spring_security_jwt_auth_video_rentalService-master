package com.crio.Spring_security_jwt_auth_Video_rentalService.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    private String id;
    private String userId; // User who rented the video
    private String videoId; // Video being rented
    private LocalDate rentedDate; // Date when the video was rented
    private LocalDate returnedDate; // Date when the video was returned
    private boolean returned = false; // To track if the video has been returned
}
