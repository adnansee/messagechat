package com.chatmessage.repository;

import antlr.collections.List;
import com.chatmessage.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** USER REPOSITORY CLASS WITH MONGOREPOSITORY EXTENDED
 *
 */
@Repository
public interface UserRepository extends MongoRepository<Users, String> {


}
