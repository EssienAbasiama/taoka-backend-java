package com.chatapp.taoka.dao.request;

import com.chatapp.taoka.Model.User;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GroupCreationRequest {
    public GroupCreationRequest() {
    }
    @NonNull
    private String title;
    @NonNull
    private List<User> members;
    @NonNull
    private User groupAuthor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getGroupAuthor() {
        return groupAuthor;
    }

    public void setGroupAuthor(User groupAuthor) {
        this.groupAuthor = groupAuthor;
    }
}
