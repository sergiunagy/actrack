package sena.activitytracker.acktrack.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {
    /*source: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-authenticationmanager */

    /*Implement the constructor - customized to our app*/

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }

        Authentication authResult = attemptAuthentication(request, response);
        if(null == authResult) {
            /* go to any next method of authenticating */
            chain.doFilter(req, res);
        } else { /* wrap up authentication, this was the only necessary layer and passed data was complete*/
            successfulAuthentication(request, response, chain, authResult);
        }
    }

    @Override
    public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        /* collect credentials from the request*/
        String username = getUserName(httpServletRequest);
        String password = getPassword(httpServletRequest);

        /* null safety */
        if((null== username) || (null== password)) {
            log.error("null credentials passed to authentication");
            return null; /* do filter method expects a null for incomplete authentications*/
        }

        log.debug("Authenticating user: " + username);

        /* build authentication token */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(token);
    }


    /**
     * Customized behaviour for successful authentication.
     * <ol>
     * <li>Sets the successful <tt>Authentication</tt> object on the
     * {@link SecurityContextHolder}</li>
     * <li>Informs the configured <tt>RememberMeServices</tt> of the successful login</li>
     * <li>Fires an {@link InteractiveAuthenticationSuccessEvent} via the configured
     * <tt>ApplicationEventPublisher</tt></li>
     * <li>Delegates additional behaviour to the {@link AuthenticationSuccessHandler}.</li>
     * </ol>
     *
     * Subclasses can override this method to continue the {@link FilterChain} after
     * successful authentication.
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     * method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }

        /* Update the SecurityContext with the new authentication */
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    private String getPassword(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getHeader("Api-Secret");
    }

    private String getUserName(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getHeader("Api-Key");
    }
}
