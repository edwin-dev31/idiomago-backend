package com.linguanova.idiomago.config.security;

import com.linguanova.idiomago.config.jwt.JwtUtil;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import com.linguanova.idiomago.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static com.linguanova.idiomago.util.AppRoutes.FRONTEND_REDIRECTION_URL;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, @Lazy UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String registrationId = ((org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

        String email = null;
        String username = null;

        switch (registrationId) {
            case "github":
                email = oAuth2User.getAttribute("email");
                username = oAuth2User.getAttribute("login");
                if (email == null) {
                    email = username + "@github.local";
                }
                break;
            case "google":
                email = oAuth2User.getAttribute("email");
                username = oAuth2User.getAttribute("name");
                break;
            case "facebook":
                email = oAuth2User.getAttribute("email");
                username = oAuth2User.getAttribute("name");
                break;
            default:
                throw new IllegalStateException("Unexpected provider: " + registrationId);
        }

        if (email == null || username == null) {
            throw new IllegalStateException("Missing user info from provider: " + registrationId);
        }

        if (userService.findByEmail(email).isEmpty()) {
            CreateUserDTO dto = new CreateUserDTO();
            dto.setUsername(username);
            dto.setEmail(email);
            dto.setPassword(UUID.randomUUID().toString());

            userService.save(dto);
        }
        Long userId = userService.findByEmail(email).get().getId();

        String jwt = jwtUtil.generateToken(email);
        String redirectUrl = FRONTEND_REDIRECTION_URL + jwt + "&userId=" + userId;

        response.sendRedirect(redirectUrl);
    }

}