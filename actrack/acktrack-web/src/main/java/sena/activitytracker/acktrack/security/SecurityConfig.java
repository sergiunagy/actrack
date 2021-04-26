package sena.activitytracker.acktrack.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Any endpoint that requires defense against common vulnerabilities can be specified here, including public ones.
     * See {@link HttpSecurity#authorizeRequests} and the `permitAll()` authorization rule
     * for more details on public endpoints.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .authorizeRequests(
                authorization->{
                    authorization.mvcMatchers(HttpMethod.GET, "/", "/webjars/**", "/css/**", "/fonts/**", "/js/**").permitAll();
                    authorization.mvcMatchers(HttpMethod.GET, "/calendar/create_booking").hasRole("ADMIN");
        })
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin().and()
        .httpBasic();
    }

    /* Password encoding provider - overrides default delegating encoder*/
    @Bean
    PasswordEncoder passwordEncoder(){
        /* Legacy encoding options: check also the password string we validate against */

        /* NOOP - When using NoOp we can disable the noop password prefix. Default is not available*/
        // return NoOpPasswordEncoder.getInstance();

        /* LDAP */
        // return new LdapShaPasswordEncoder();

        /* SHA256 - supports custom salt as 'secret', is too fast to be safe*/
        // return new StandardPasswordEncoder();

        /* BCRYPT - current default Spring encoder. Supports encoding strength configuration*/
        //return new BCryptPasswordEncoder(12); /* default strengh is 10*/

        /* Factory method hashes several encoder twith their names as keys.
        So multiple parallel encodings are supported*/
        return ActrackPasswordEncoderFactory
                .createDelegatingPasswordEncoder();
    }

    /*Fluent API UserDetailsService override:
    * {noop} will store with no encryption or hashing */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("guru")
                //.password("guru") /*NoOp test*/
                // .password("{SSHA}DFWRykdSSjNHMcvNoFdZrMyNw6a82f73B9DyxA==") /*ldap test, use "guru" ldap encoded*/
                //.password("5922e9b6cef88d0e3bbcc2e74c7516f0cbdc81e36a60024c276b018c798d7ce709011b58247b54ae")
                //.password("$2a$12$vhGccRPHUINK3enIIczWF.UQberXTqFwn4Wtvc3Qjr3upeFJYlvu6")
                .password("{bcrypt12}$2a$12$vhGccRPHUINK3enIIczWF.UQberXTqFwn4Wtvc3Qjr3upeFJYlvu6") /*Delegating Password Encoder - use encoder id from factory class*/
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("user")
                .roles("USER");
    }

    /*Override UserDetails with our own implementation*/
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService(){
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("guru")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        /*return our customized Details implementation - this will avoid triggerring the yml config*/
//        return new InMemoryUserDetailsManager(admin,user);
//    }
}
