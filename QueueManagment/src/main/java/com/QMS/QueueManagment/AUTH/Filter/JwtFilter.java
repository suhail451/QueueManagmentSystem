package com.QMS.QueueManagment.AUTH.Filter;

import com.QMS.QueueManagment.AUTH.Jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String header=request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {

           filterChain.doFilter(request,response);
           return;
        }

        String jwtToken = header.substring(7);

        String username = jwtService.extractUserName(jwtToken);

try{
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // steps 3-7 go inside here
            if(jwtService.validateToken(jwtToken)){

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList()

                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }}catch (Exception e){

        throw new RuntimeException("not valid token");
}

        filterChain.doFilter(request,response);

    }




    }

