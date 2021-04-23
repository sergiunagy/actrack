package sena.activitytracker.acktrack.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
//                    authorization.antMatchers("/",
//                            "/webjars/**",
//                            "/css/**",
//                            "/fonts/**",
//                            "/js/**").permitAll();
                    authorization.mvcMatchers(HttpMethod.GET, "/", "/webjars/**", "/css/**", "/fonts/**", "/js/**").permitAll();
                    authorization.mvcMatchers(HttpMethod.GET, "/calendar/create_booking").hasRole("ADMIN");
        })
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin().and()
        .httpBasic();
    }

    /*Override UserDetails with our own implementation*/
    @Override
    @Bean
    protected UserDetailsService userDetailsService(){

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("guru")
                .password("guru")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        /*return our customized Details implementation - this will avoid triggerring the yml config*/
        return new InMemoryUserDetailsManager(admin,user);
    }
}
