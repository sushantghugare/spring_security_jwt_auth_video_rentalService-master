package com.crio.Spring_security_jwt_auth_Video_rentalService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;
    private String title;
    private String director;
    private String genre;
    private boolean available = true; // Default to available
}
