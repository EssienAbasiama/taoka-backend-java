package com.chatapp.taoka.Model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "friends")
public class Friends {
    @Id
    private String id;
    private User user;

}
