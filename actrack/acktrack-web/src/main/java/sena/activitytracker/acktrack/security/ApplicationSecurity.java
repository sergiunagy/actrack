package sena.activitytracker.acktrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*
TODO:
 Step 1 : set up a basic http authentication - we only neeed the UserDetails at this point
 Step 2 : lock access to web links (no service restrictions) for unauthenticated users
 Step 3 : implement a higher level security
 Step 4 : implement service layer level protection and authorization
 ONGOING: Step1

 docs:
  - https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication
   - https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-mechanisms
*/

@Profile("test")
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**"); /* access to website is completely open*/
        /* todo : secure access to all. Authenticated users are the only ones to have access */
    }

    @Bean(name="authenticationManager")
    public AuthenticationManager authenticationManager() throws Exception{

        return super.authenticationManager();
    }
}
