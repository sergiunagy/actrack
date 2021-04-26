package sena.activitytracker.acktrack.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
public class PasswordEncodingTest {

    static final String TEST_PASSWORD = "password";

    //@Autowired
    WebApplicationContext wac;


    MockMvc mvc ;


    @Test
    void hashingTest(){

        System.out.println(DigestUtils.md5DigestAsHex(TEST_PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salted = TEST_PASSWORD + "ThisIsMySALTVALUE";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes(StandardCharsets.UTF_8)));

    }

    @Test
    void testNoOp(){

        PasswordEncoder noop = NoOpPasswordEncoder.getInstance();

        System.out.println(noop.encode(TEST_PASSWORD));
    }

    @Test
    void testLDAP(){

        PasswordEncoder ldap = new LdapShaPasswordEncoder();

        /* ldap provides dynamic output - adds random salt*/
        System.out.println(ldap.encode(TEST_PASSWORD));
        System.out.println(ldap.encode(TEST_PASSWORD));

        /* check that password matching works even if encoding is dynamic*/
        assertTrue(ldap.matches(TEST_PASSWORD,
                                ldap.encode(TEST_PASSWORD)));
        /* generate for the password test*/
        System.out.println(ldap.encode("guru"));
    }

    @Test
    void testSha256(){

        PasswordEncoder sha = new StandardPasswordEncoder();

        System.out.println(sha.encode(TEST_PASSWORD));
        System.out.println(sha.encode(TEST_PASSWORD));
        System.out.println(sha.encode("guru"));


    }

    @Test
    void testBCrypt(){

        PasswordEncoder bcrypt = new BCryptPasswordEncoder(12);

        System.out.println(bcrypt.encode(TEST_PASSWORD));
        System.out.println(bcrypt.encode(TEST_PASSWORD));
        System.out.println(bcrypt.encode("guru"));

    }
}
