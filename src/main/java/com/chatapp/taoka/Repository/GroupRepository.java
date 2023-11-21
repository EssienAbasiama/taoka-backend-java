package com.chatapp.taoka.Repository;

import com.chatapp.taoka.Model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository  extends MongoRepository<Group, String> {
}
