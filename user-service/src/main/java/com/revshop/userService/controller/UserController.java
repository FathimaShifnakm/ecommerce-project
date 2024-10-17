package com.revshop.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revshop.userService.dto.UserCreateDTO;
import com.revshop.userService.dto.UserDTO;
import com.revshop.userService.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
		
	@Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserDTO createdUser = userService.createUser(userCreateDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);  // Return HTTP 201
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);  // Return HTTP 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return HTTP 404
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);  // Return HTTP 200
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("id") Long id, @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO updatedUser = userService.updateUser(id, userCreateDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);  // Return HTTP 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return HTTP 404
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return HTTP 204
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return HTTP 404
        }
    }

}
