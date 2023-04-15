package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JWTFilter implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String header = httpServletRequest.getHeader("authorization");

        if(httpServletRequest == null || !header.startsWith("Bearer")) {
            throw new ServletException("Wrong or empty header");
        }
        else {
            try {
                String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey("admin").parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (Exception e) {
                throw new ServletException("Wrong key");
            }
        }
        chain.doFilter(request, response);
    }
}
