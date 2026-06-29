package com.linguanova.idiomago.presentation.controller;

import com.linguanova.idiomago.config.jwt.JwtUtil;
import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.auth.AuthRequest;
import com.linguanova.idiomago.presentation.dto.auth.AuthResponse;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.service.impl.EmailVerificationService;
import com.linguanova.idiomago.service.interfaces.IUserService;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.mapper.impl.user.UpdateUserMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static com.linguanova.idiomago.util.AppRoutes.FRONTEND_BASE_URL;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	private final IUserService userService;
	private final EmailVerificationService emailVerificationService;
	private final UpdateUserMapper updateUserMapper;

    private static final String MESSAGE_KEY = "message";
	public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          UserDetailsService userDetailsService, IUserService userService,
                          EmailVerificationService emailVerificationService, PasswordEncoder passwordEncoder, UpdateUserMapper updateUserMapper) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
        this.updateUserMapper = updateUserMapper;
    }

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
		Optional<UserEntity> optionalUser = userService.findByEmail(request.getEmail());
		UserEntity user = optionalUser.orElseThrow(() ->
				new ResourceNotFoundException("User not found: " + request.getEmail()));

		if (!Boolean.TRUE.equals(user.getVerified())) {
			return ResponseEntity
					.status(403)
					.body(Map.of(MESSAGE_KEY, "You must verify your email before logging in."));
		}
		var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		authManager.authenticate(auth);

		var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		var jwt = jwtUtil.generateToken(userDetails.getUsername());

		return ResponseEntity.ok(new AuthResponse(user.getId(), jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@Valid @RequestBody CreateUserDTO dto) {
		Optional<UserEntity> existing = userService.findByEmail(dto.getEmail());

		if (existing.isPresent()) {
			UserEntity user = existing.get();
			if (Boolean.TRUE.equals(user.getVerified())) {
				return ResponseEntity.badRequest().body(Map.of(
                        MESSAGE_KEY, "This email is already registered and verified."
				));
			} else {
				String token = jwtUtil.generateToken(user.getEmail());
				emailVerificationService.sendVerificationEmail(user.getEmail(), user.getUsername(), token);
				return ResponseEntity.ok(Map.of(
                        MESSAGE_KEY, "You're already registered. We have resent the verification email."
				));
			}
		}

		UserDTO created = userService.save(dto);

		String token = jwtUtil.generateToken(dto.getEmail());
		emailVerificationService.sendVerificationEmail(dto.getEmail(), dto.getUsername(), token);

		return ResponseEntity.ok(Map.of(
                MESSAGE_KEY, "Successful registration. Please check your email to activate your account."
		));
	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
		return ResponseEntity.ok(Map.of(MESSAGE_KEY, "Sesión cerrada"));
	}

	@GetMapping("/verify-email")
	public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam String token) {
        final String MESSAGE_KEY_LOCATION = "Location";
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(302).header(MESSAGE_KEY_LOCATION, FRONTEND_BASE_URL + "/email-verification?status=invalid").build();
		}

		String email = jwtUtil.extractEmail(token);
		UserEntity user = userService.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		if (Boolean.TRUE.equals(user.getVerified())) {
			return ResponseEntity.status(302).header(MESSAGE_KEY_LOCATION, FRONTEND_BASE_URL + "/email-verification?status=already").build();
		}

		user.setVerified(true);
		userService.update(user.getId(), updateUserMapper.mapTo(user));

		return ResponseEntity.status(302).header(MESSAGE_KEY_LOCATION, FRONTEND_BASE_URL + "/email-verification?status=success").build();
	}
}


