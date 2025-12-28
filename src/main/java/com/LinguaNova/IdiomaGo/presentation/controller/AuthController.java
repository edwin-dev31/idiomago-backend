package com.LinguaNova.IdiomaGo.presentation.controller;

import com.LinguaNova.IdiomaGo.config.jwt.JwtUtil;
import com.LinguaNova.IdiomaGo.persistence.entity.UserEntity;
import com.LinguaNova.IdiomaGo.presentation.dto.auth.AuthRequest;
import com.LinguaNova.IdiomaGo.presentation.dto.auth.AuthResponse;
import com.LinguaNova.IdiomaGo.presentation.dto.user.CreateUserDTO;
import com.LinguaNova.IdiomaGo.presentation.dto.user.UserDTO;
import com.LinguaNova.IdiomaGo.service.impl.EmailVerificationService;
import com.LinguaNova.IdiomaGo.service.interfaces.IUserService;
import com.LinguaNova.IdiomaGo.util.exception.ResourceNotFoundException;
import com.LinguaNova.IdiomaGo.util.mapper.impl.user.UpdateUserMapper;
import com.LinguaNova.IdiomaGo.util.mapper.impl.user.UserMapper;
import jakarta.servlet.http.Cookie;
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

import static com.LinguaNova.IdiomaGo.util.AppRoutes.FRONTEND_BASE_URL;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	private final IUserService userService;
	private final EmailVerificationService emailVerificationService;
	private final UpdateUserMapper updateUserMapper;

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
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		Optional<UserEntity> optionalUser = userService.findByEmail(request.getEmail());
		UserEntity user = optionalUser.orElseThrow(() ->
				new ResourceNotFoundException("User not found: " + request.getEmail()));

		if (!Boolean.TRUE.equals(user.getVerified())) {
			return ResponseEntity
					.status(403)
					.body(Map.of("message", "You must verify your email before logging in."));
		}

		System.out.println("Hola: " + request.getEmail() +"  " +request.getPassword());
		var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		authManager.authenticate(auth);

		var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		var jwt = jwtUtil.generateToken(userDetails.getUsername());

		return ResponseEntity.ok(new AuthResponse(user.getId(), jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody CreateUserDTO dto) {
		Optional<UserEntity> existing = userService.findByEmail(dto.getEmail());

		if (existing.isPresent()) {
			UserEntity user = existing.get();
			if (Boolean.TRUE.equals(user.getVerified())) {
				return ResponseEntity.badRequest().body(Map.of(
						"message", "This email is already registered and verified."
				));
			} else {
				String token = jwtUtil.generateToken(user.getEmail());
				emailVerificationService.sendVerificationEmail(user.getEmail(), user.getUsername(), token);
				return ResponseEntity.ok(Map.of(
						"message", "You're already registered. We have resent the verification email."
				));
			}
		}

		UserDTO created = userService.save(dto);

		String token = jwtUtil.generateToken(dto.getEmail());
		emailVerificationService.sendVerificationEmail(dto.getEmail(), dto.getUsername(), token);

		return ResponseEntity.ok(Map.of(
				"message", "Successful registration. Please check your email to activate your account."
		));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
	}

	@GetMapping("/verify-email")
	public ResponseEntity<?> verifyEmail(@RequestParam String token) {
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(302).header("Location", FRONTEND_BASE_URL + "/email-verification?status=invalid").build();
		}

		String email = jwtUtil.extractEmail(token);
		UserEntity user = userService.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		if (Boolean.TRUE.equals(user.getVerified())) {
			return ResponseEntity.status(302).header("Location", FRONTEND_BASE_URL + "/email-verification?status=already").build();
		}

		user.setVerified(true);
		userService.update(user.getId(), updateUserMapper.mapTo(user));

		return ResponseEntity.status(302).header("Location", FRONTEND_BASE_URL + "/email-verification?status=success").build();
	}



}


