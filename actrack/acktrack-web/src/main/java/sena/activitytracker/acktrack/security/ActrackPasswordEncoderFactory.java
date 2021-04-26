package sena.activitytracker.acktrack.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/***
 * Custom password encoder factory.
 * Allows mapping several encoders.
 * Allows adding new encoders and switching between them without exeternal code changes
 */
public class ActrackPasswordEncoderFactory {

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        /* Default encoder and encoding complexity*/
        encoders.put(encodingId, new BCryptPasswordEncoder());
        /* Customized - higher complexity variant*/
        encoders.put("bcrypt12", new BCryptPasswordEncoder(12));

        /* Legacy encoders - for testing purposes*/
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    private ActrackPasswordEncoderFactory() {}
}
