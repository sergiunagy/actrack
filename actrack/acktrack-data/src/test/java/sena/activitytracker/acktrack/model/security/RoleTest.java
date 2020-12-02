package sena.activitytracker.acktrack.model.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sena.activitytracker.acktrack.model.BaseDomTest;
import sena.activitytracker.acktrack.model.security.Role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest extends BaseDomTest {

    Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();
    }

    @Test
    public void builderTest(){

        Role role = Role.builder().name("Test").build();

        assertNotNull(role);
        assertTrue(role.isNew());
    }
}