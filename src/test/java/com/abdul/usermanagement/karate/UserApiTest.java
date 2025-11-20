package com.abdul.usermanagement.karate;

import com.intuit.karate.junit5.Karate;

public class UserApiTest {

    @Karate.Test
    Karate testUsers() {
        return Karate.run("user-api").relativeTo(getClass());
    }
}
