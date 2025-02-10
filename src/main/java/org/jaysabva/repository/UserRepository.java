package org.jaysabva.repository;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    boolean existsUserByUserName(String userName);

    boolean existsUserByPhoneno(String phoneno);

    Optional<User> findByUserName(String userName);

    void deleteByUserName(String username);

    Optional<User> findByUserNameAndPassword(String username, String password);

    User save(User user);

    List<User> findAll();
}