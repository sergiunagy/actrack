package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void dummy(){

        System.out.println("Placeholder test");
    }
}