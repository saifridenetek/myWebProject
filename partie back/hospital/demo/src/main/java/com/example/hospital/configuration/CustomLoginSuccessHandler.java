package com.example.hospital.configuration;

import com.example.hospital.entity.User;
import com.example.hospital.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    // La ligne GlobalData.cart.clear() a été supprimée

    User user = (User) authentication.getPrincipal();
    String token = jwtService.generateToken(Map.of(), (UserDetails) user);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write("{\"token\": \"" + token + "\"}");
    // Redirect user to appointment list
    response.sendRedirect("/appointments");

  }
}