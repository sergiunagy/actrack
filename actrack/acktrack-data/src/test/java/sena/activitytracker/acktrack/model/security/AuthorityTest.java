package sena.activitytracker.acktrack.model.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {

    Authority authority;


    @BeforeEach
    void setUp() {
    }

    @Test
    void builderTest(){

        authority = Authority.builder().permission("Admin").build();

        assertNotNull(authority);
        assertTrue(authority.isNew());
    }
}