package com.abdul.usermanagement.service;

import com.abdul.usermanagement.dto.UserRequest;
import com.abdul.usermanagement.dto.UserResponse;
import com.abdul.usermanagement.exception.UserNotFoundException;
import com.abdul.usermanagement.model.User;
import com.abdul.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Get all users
     *
     * @return list of all users
     */
    public List<UserResponse> getAllUsers() {
        log.debug("Getting all users");
        List<User> users = userRepository.findAll();
        log.info("Retrieved {} users", users.size());
        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get user by ID
     *
     * @param id the user ID
     * @return user response
     * @throws UserNotFoundException if user not found
     */
    public UserResponse getUserById(String id) {
        log.debug("Getting user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Retrieved user with id: {}", id);
        return mapToResponse(user);
    }

    /**
     * Create a new user
     *
     * @param request user request
     * @return created user response
     */
    public UserResponse createUser(UserRequest request) {
        log.debug("Creating user with firstName: {}, lastName: {}, age: {}",
                request.firstName(), request.lastName(), request.age());

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .address(request.address())
                .age(request.age())
                .build();

        User savedUser = userRepository.save(user);
        log.info("Created user with id: {}", savedUser.getId());
        return mapToResponse(savedUser);
    }

    /**
     * Update an existing user
     *
     * @param id the user ID
     * @param request user request
     * @return updated user response
     * @throws UserNotFoundException if user not found
     */
    public UserResponse updateUser(String id, UserRequest request) {
        log.debug("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setAddress(request.address());
        user.setAge(request.age());

        User updatedUser = userRepository.save(user);
        log.info("Updated user with id: {}", id);
        return mapToResponse(updatedUser);
    }

    /**
     * Delete a user
     *
     * @param id the user ID
     * @throws UserNotFoundException if user not found
     */
    public void deleteUser(String id) {
        log.debug("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    /**
     * Search users by first name and/or last name
     *
     * @param firstName optional first name
     * @param lastName optional last name
     * @return list of matching users
     */
    public List<UserResponse> searchUsers(String firstName, String lastName) {
        log.debug("Searching users with firstName: {}, lastName: {}", firstName, lastName);

        List<User> users;

        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            users = userRepository.findByFirstNameAndLastNameRegex(firstName, lastName);
        } else if (firstName != null && !firstName.isEmpty()) {
            users = userRepository.findByFirstNameRegex(firstName);
        } else if (lastName != null && !lastName.isEmpty()) {
            users = userRepository.findByLastNameRegex(lastName);
        } else {
            users = userRepository.findAll();
        }

        log.info("Found {} users matching search criteria", users.size());
        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Map User entity to UserResponse DTO
     *
     * @param user the user entity
     * @return user response DTO
     */
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
