package com.abdul.usermanagement.repository;

import com.abdul.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Search users by first name using case-insensitive regex matching
     *
     * @param firstName the first name to search for
     * @return list of users matching the first name
     */
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' } }")
    List<User> findByFirstNameRegex(String firstName);

    /**
     * Search users by last name using case-insensitive regex matching
     *
     * @param lastName the last name to search for
     * @return list of users matching the last name
     */
    @Query("{ 'lastName': { $regex: ?0, $options: 'i' } }")
    List<User> findByLastNameRegex(String lastName);

    /**
     * Search users by both first name and last name using case-insensitive regex matching
     *
     * @param firstName the first name to search for
     * @param lastName the last name to search for
     * @return list of users matching both criteria
     */
    @Query("{ $and: [ { 'firstName': { $regex: ?0, $options: 'i' } }, { 'lastName': { $regex: ?1, $options: 'i' } } ] }")
    List<User> findByFirstNameAndLastNameRegex(String firstName, String lastName);
}
