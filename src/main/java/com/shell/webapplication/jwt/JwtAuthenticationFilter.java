package com.shell.webapplication.jwt;

import com.shell.webapplication.auth.service.CustomUserDetailsService;
import com.shell.webapplication.auth.dto.CustomUserDetails;
import com.shell.webapplication.constent.AppConstant;
import com.shell.webapplication.context.UserContext;
import com.shell.webapplication.exception.customexception.InvalidTokenException;
import com.shell.webapplication.exception.customexception.TokenExpirationException;
import com.shell.webapplication.exception.customexception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * It will Execute Automatically for every client request or servlet request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String ACCESS_TOKEN = jwtUtils.tokenResolver(request); // access token will extrated here
        String userName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(ACCESS_TOKEN)) {
            try {
                userName = jwtUtils.extractAllClaims(ACCESS_TOKEN).getSubject();
            } catch (JwtException e) {
                throw new UnauthorizedException();
            } catch (Exception e) {
                throw new InvalidTokenException();
            }
        }

        if (Objects.isNull(authentication) && Objects.nonNull(userName) && !jwtUtils.isTokenExpired(ACCESS_TOKEN)) {
            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService
                    .loadUserByUsername(userName);
            if (jwtUtils.validateToken(ACCESS_TOKEN)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                UserContext.setLoggedInUserId(customUserDetails.getUser().getUserId());
            } else {
                throw new InvalidTokenException();
            }
        }
        filterChain.doFilter(request, response);
    }

}
