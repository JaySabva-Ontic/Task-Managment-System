package org.jaysabva.repository;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsUserByUserName(String userName);

    boolean existsUserByPhoneno(String phoneno);

    User findByUserName(String userName);

    void deleteByUserName(String username);

    User findByUserNameAndPassword(String username, String password);
}