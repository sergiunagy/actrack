package sena.activitytracker.acktrack.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {
    /*source: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-authenticationmanager */

    /*Implement the constructor - customized to our app*/

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        /* collect credentials from the request*/
        String username = getUserName(httpServletRequest);
        String password = getPassword(httpServletRequest);

        /* null safety */
        if(null== username) username="";
        if(null== password) password="";
        if((null== username) || (null== password)) log.error("null credentials passed to authentication");

        log.debug("Authenticating user: " + username);

        /* build authentication token */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(token);
    }

    private String getPassword(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getHeader("Api-Secret");
    }

    private String getUserName(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getHeader("Api-Key");
    }
}
