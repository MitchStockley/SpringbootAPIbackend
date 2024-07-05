package com.testREST.RegisterApp.repository;

import com.testREST.RegisterApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//   List<User> findByUser(Long userId);
//
//   //find by id
//    Optional<User> findById(Long userId);



    User findByUsername(String username);



//    List<User> findByEmail(String email);

}
