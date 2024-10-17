package com.revshop.userService.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userService.dto.UserCreateDTO;
import com.revshop.userService.dto.UserDTO;
import com.revshop.userService.model.User;
import com.revshop.userService.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserDTO createUser(UserCreateDTO userCreateDTO) {
        // Create a new User entity from the DTO
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(userCreateDTO.getPassword());

        // Save the user entity in the database
        User savedUser = userRepository.save(user);

        // Convert the saved User entity to a UserDTO and return
        return mapToUserDTO(savedUser);
    }
	
	public UserDTO getUserById(Long id) {
        // Retrieve the user entity by ID
        Optional<User> userOptional = userRepository.findById(id);

        // If user exists, convert to DTO, else return null
        return userOptional.map(this::mapToUserDTO).orElse(null);
    }
	
	// Method to get all users from the database
    public List<UserDTO> getAllUsers() {
        // Retrieve all users and map them to DTOs
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    // Method to update an existing user by their ID
    public UserDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        // Find the user by ID
        Optional<User> userOptional = userRepository.findById(id);

        // If user exists, update their details and save
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userCreateDTO.getUsername());
            user.setEmail(userCreateDTO.getEmail());
            user.setPassword(userCreateDTO.getPassword());

            User updatedUser = userRepository.save(user);
            return mapToUserDTO(updatedUser);
        } else {
            return null; // or throw an exception if preferred
        }
    }

    // Method to delete a user by their ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Private helper method to map a User entity to a UserDTO
    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}
