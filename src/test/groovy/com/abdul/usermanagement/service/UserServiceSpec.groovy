package com.abdul.usermanagement.service

import com.abdul.usermanagement.dto.UserRequest
import com.abdul.usermanagement.dto.UserResponse
import com.abdul.usermanagement.exception.UserNotFoundException
import com.abdul.usermanagement.model.User
import com.abdul.usermanagement.repository.UserRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class UserServiceSpec extends Specification {

    UserRepository userRepository = Mock()

    @Subject
    UserService userService = new UserService(userRepository)

    def "getAllUsers should return all users"() {
        given: "a list of users in the repository"
        def user1 = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        def user2 = User.builder()
                .id("2")
                .firstName("Jane")
                .lastName("Smith")
                .address("456 Oak Ave")
                .age(25)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        def users = [user1, user2]

        when: "getAllUsers is called"
        def result = userService.getAllUsers()

        then: "repository findAll is called"
        1 * userRepository.findAll() >> users

        and: "all users are returned"
        result.size() == 2
        result[0].firstName == "John"
        result[1].firstName == "Jane"
    }

    def "getUserById should return user when found"() {
        given: "a user exists in the repository"
        def userId = "1"
        def user = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        when: "getUserById is called"
        def result = userService.getUserById(userId)

        then: "repository findById is called"
        1 * userRepository.findById(userId) >> Optional.of(user)

        and: "the user is returned"
        result.id == userId
        result.firstName == "John"
        result.lastName == "Doe"
    }

    def "getUserById should throw UserNotFoundException when not found"() {
        given: "a user does not exist in the repository"
        def userId = "999"

        when: "getUserById is called"
        userService.getUserById(userId)

        then: "repository findById is called"
        1 * userRepository.findById(userId) >> Optional.empty()

        and: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
    }

    def "createUser should save and return new user"() {
        given: "a user request"
        def request = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .age(30)
                .build()

        def savedUser = User.builder()
                .id("1")
                .firstName(request.firstName)
                .lastName(request.lastName)
                .address(request.address)
                .age(request.age)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        when: "createUser is called"
        def result = userService.createUser(request)

        then: "repository save is called"
        1 * userRepository.save(_ as User) >> savedUser

        and: "the created user is returned"
        result.id == "1"
        result.firstName == "John"
        result.lastName == "Doe"
        result.age == 30
    }

    def "updateUser should update and return user when found"() {
        given: "an existing user and update request"
        def userId = "1"
        def existingUser = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        def request = UserRequest.builder()
                .firstName("Johnny")
                .lastName("Doe Updated")
                .address("789 New St")
                .age(31)
                .build()

        def updatedUser = User.builder()
                .id(userId)
                .firstName(request.firstName)
                .lastName(request.lastName)
                .address(request.address)
                .age(request.age)
                .createdAt(existingUser.createdAt)
                .updatedAt(LocalDateTime.now())
                .build()

        when: "updateUser is called"
        def result = userService.updateUser(userId, request)

        then: "repository findById is called"
        1 * userRepository.findById(userId) >> Optional.of(existingUser)

        and: "repository save is called"
        1 * userRepository.save(_ as User) >> updatedUser

        and: "the updated user is returned"
        result.id == userId
        result.firstName == "Johnny"
        result.lastName == "Doe Updated"
        result.age == 31
    }

    def "updateUser should throw UserNotFoundException when not found"() {
        given: "a user does not exist"
        def userId = "999"
        def request = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .age(30)
                .build()

        when: "updateUser is called"
        userService.updateUser(userId, request)

        then: "repository findById is called"
        1 * userRepository.findById(userId) >> Optional.empty()

        and: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
    }

    def "deleteUser should delete user when found"() {
        given: "a user exists"
        def userId = "1"

        when: "deleteUser is called"
        userService.deleteUser(userId)

        then: "repository existsById is called"
        1 * userRepository.existsById(userId) >> true

        and: "repository deleteById is called"
        1 * userRepository.deleteById(userId)
    }

    def "deleteUser should throw UserNotFoundException when not found"() {
        given: "a user does not exist"
        def userId = "999"

        when: "deleteUser is called"
        userService.deleteUser(userId)

        then: "repository existsById is called"
        1 * userRepository.existsById(userId) >> false

        and: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
    }

    def "searchUsers should search by firstName only"() {
        given: "users matching firstName"
        def user = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        when: "searchUsers is called with firstName only"
        def result = userService.searchUsers("John", null)

        then: "repository findByFirstNameRegex is called"
        1 * userRepository.findByFirstNameRegex("John") >> [user]

        and: "matching users are returned"
        result.size() == 1
        result[0].firstName == "John"
    }

    def "searchUsers should search by lastName only"() {
        given: "users matching lastName"
        def user = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        when: "searchUsers is called with lastName only"
        def result = userService.searchUsers(null, "Doe")

        then: "repository findByLastNameRegex is called"
        1 * userRepository.findByLastNameRegex("Doe") >> [user]

        and: "matching users are returned"
        result.size() == 1
        result[0].lastName == "Doe"
    }

    def "searchUsers should search by both firstName and lastName"() {
        given: "users matching both firstName and lastName"
        def user = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        when: "searchUsers is called with both firstName and lastName"
        def result = userService.searchUsers("John", "Doe")

        then: "repository findByFirstNameAndLastNameRegex is called"
        1 * userRepository.findByFirstNameAndLastNameRegex("John", "Doe") >> [user]

        and: "matching users are returned"
        result.size() == 1
        result[0].firstName == "John"
        result[0].lastName == "Doe"
    }

    def "searchUsers should return all users when no search criteria provided"() {
        given: "multiple users in the repository"
        def user1 = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        def user2 = User.builder()
                .id("2")
                .firstName("Jane")
                .lastName("Smith")
                .age(25)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()

        when: "searchUsers is called with no criteria"
        def result = userService.searchUsers(null, null)

        then: "repository findAll is called"
        1 * userRepository.findAll() >> [user1, user2]

        and: "all users are returned"
        result.size() == 2
    }
}
