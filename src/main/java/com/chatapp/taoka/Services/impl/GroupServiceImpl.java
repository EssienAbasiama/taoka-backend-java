package com.chatapp.taoka.Services.impl;

import com.chatapp.taoka.Model.Group;
import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Repository.GroupRepository;
import com.chatapp.taoka.Services.GroupService;
import com.chatapp.taoka.dao.request.GroupCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    final GroupRepository groupRepository;
    @Override
    public Group createGroup(GroupCreationRequest request, User groupAuthor) {
        var group = Group.builder()
                .title(request.getTitle())
                .members(request.getMembers())
                .groupAuthor(groupAuthor)
                .createdAt(LocalDateTime.now())
                .build();
        return groupRepository.save(group);
    }
}
