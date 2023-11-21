package com.chatapp.taoka.Model;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "group")
public class Group {
    @Id
    private String id;
    private String title;
    @OneToMany
    private List<User> members;
    private LocalDateTime createdAt;
    private User groupAuthor;
}
