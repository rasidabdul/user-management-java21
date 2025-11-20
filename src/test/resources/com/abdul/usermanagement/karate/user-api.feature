Feature: User Management API

  Background:
    * url 'http://localhost:8080/api'
    * def userRequest =
      """
      {
        "firstName": "John",
        "lastName": "Doe",
        "address": "123 Main St",
        "age": 30
      }
      """

  Scenario: Create a new user
    Given path 'users'
    And request userRequest
    When method POST
    Then status 201
    And match response.firstName == 'John'
    And match response.lastName == 'Doe'
    And match response.age == 30
    And match response.id != null
    And match response.createdAt != null
    * def createdUserId = response.id

  Scenario: Get all users
    Given path 'users'
    When method GET
    Then status 200
    And match response == '#array'

  Scenario: Create user and get by ID
    # Create user first
    Given path 'users'
    And request userRequest
    When method POST
    Then status 201
    * def userId = response.id

    # Get the created user
    Given path 'users', userId
    When method GET
    Then status 200
    And match response.id == userId
    And match response.firstName == 'John'
    And match response.lastName == 'Doe'

  Scenario: Update user
    # Create user first
    Given path 'users'
    And request userRequest
    When method POST
    Then status 201
    * def userId = response.id

    # Update the user
    * def updateRequest =
      """
      {
        "firstName": "Johnny",
        "lastName": "Doe Updated",
        "address": "456 Oak Ave",
        "age": 31
      }
      """
    Given path 'users', userId
    And request updateRequest
    When method PUT
    Then status 200
    And match response.id == userId
    And match response.firstName == 'Johnny'
    And match response.lastName == 'Doe Updated'
    And match response.age == 31

  Scenario: Delete user
    # Create user first
    Given path 'users'
    And request userRequest
    When method POST
    Then status 201
    * def userId = response.id

    # Delete the user
    Given path 'users', userId
    When method DELETE
    Then status 204

    # Verify user is deleted
    Given path 'users', userId
    When method GET
    Then status 404

  Scenario: Get user by invalid ID returns 404
    Given path 'users', '999999999999999999999999'
    When method GET
    Then status 404
    And match response.error == 'Not Found'

  Scenario: Create user with invalid data returns 400
    * def invalidRequest =
      """
      {
        "firstName": "",
        "age": 0
      }
      """
    Given path 'users'
    And request invalidRequest
    When method POST
    Then status 400
    And match response.error == 'Bad Request'
    And match response.fieldErrors != null

  Scenario: Search users by firstName
    # Create test users
    * def user1 = { "firstName": "Alice", "lastName": "Smith", "age": 25 }
    * def user2 = { "firstName": "Bob", "lastName": "Johnson", "age": 30 }
    * def user3 = { "firstName": "Alice", "lastName": "Brown", "age": 28 }

    Given path 'users'
    And request user1
    When method POST
    Then status 201

    Given path 'users'
    And request user2
    When method POST
    Then status 201

    Given path 'users'
    And request user3
    When method POST
    Then status 201

    # Search by firstName
    Given path 'users/search'
    And param firstName = 'Alice'
    When method GET
    Then status 200
    And match response == '#array'
    And match each response contains { firstName: 'Alice' }

  Scenario: Search users by lastName
    # Create test user
    * def user = { "firstName": "Charlie", "lastName": "Davis", "age": 35 }

    Given path 'users'
    And request user
    When method POST
    Then status 201

    # Search by lastName
    Given path 'users/search'
    And param lastName = 'Davis'
    When method GET
    Then status 200
    And match response == '#array'

  Scenario: Search users by firstName and lastName
    # Create test user
    * def user = { "firstName": "David", "lastName": "Miller", "age": 40 }

    Given path 'users'
    And request user
    When method POST
    Then status 201

    # Search by both firstName and lastName
    Given path 'users/search'
    And param firstName = 'David'
    And param lastName = 'Miller'
    When method GET
    Then status 200
    And match response == '#array'
