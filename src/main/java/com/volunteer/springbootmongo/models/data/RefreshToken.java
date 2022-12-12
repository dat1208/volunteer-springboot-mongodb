package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;

@Data
@Document
public class RefreshToken {
    @Id
    private String id;
    @DocumentReference
    private User owner;

    //getters and setters
}
