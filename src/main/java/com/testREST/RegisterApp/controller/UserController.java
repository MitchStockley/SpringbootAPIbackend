package com.testREST.RegisterApp.controller;

import com.testREST.RegisterApp.model.User;
import com.testREST.RegisterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;





    @GetMapping("/users") // http://localhost:8081/api/users // finds all users
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}") // http://localhost:8081/api/users/1 // finds user by id
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> userdata = userRepository.findById(id);

       if (userdata.isPresent()){
           return new ResponseEntity<>(userdata.get(), HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }


//    @PostMapping("/users") // http://localhost:8081/api/users // creates a user
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        try {
//            User _user = userRepository.save(new User(user.getUsername(), user.getPassword(), user.getEmail()));
//            return new ResponseEntity<>(_user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/users") // http://localhost:8081/api/users // creates a user
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            User _user = userRepository.save(new User(user.getUsername(), encodedPassword, user.getEmail()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/users/{id}") // http://localhost:8081/api/users/1 // updates user by id
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> userdata = userRepository.findById(id);

        if (userdata.isPresent()) {
            User _user = userdata.get();
            _user.setUsername(user.getUsername());
            _user.setPassword(_user.getPassword());
            _user.setEmail(user.getEmail());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/users/{id}") // http://localhost:8081/api/users/1 // deletes user by id
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //login user
//    @PostMapping("/login")
//    public ResponseEntity <User> loginUser(@RequestBody User user) {
//
//        try {
//            if (userRepository.findByUsername(user.getUsername()) != null) {
//                User _user = userRepository.findByUsername(user.getUsername());
//                if (_user.getPassword().equals(user.getPassword())) {
//
//                    return new ResponseEntity<>(_user, HttpStatus.OK);
//
//                } else {
//                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//                }
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        try {
            Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
            if (userOptional.isPresent()) {
                User storedUser = userOptional.get();
                // Compare the encrypted password from the database with the provided password
                if (passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
                    // Passwords match, return the user
                    return new ResponseEntity<>(storedUser, HttpStatus.OK);
                } else {
                    // Passwords don't match, return unauthorized status
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                // User not found, return not found status
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle any exceptions
            throw new RuntimeException(e);
        }
    }





    }

