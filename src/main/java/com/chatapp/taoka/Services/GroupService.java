package com.chatapp.taoka.Services;

import com.chatapp.taoka.Model.Group;
import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.dao.request.GroupCreationRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GroupService {
    Group createGroup(GroupCreationRequest request, User groupAuthor);
}
