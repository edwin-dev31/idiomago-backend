package com.LinguaNova.IdiomaGo.config.security;

import com.LinguaNova.IdiomaGo.config.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

import static com.LinguaNova.IdiomaGo.util.AppRoutes.FRONTEND_BASE_URL;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.oAuth2SuccessHandler = oAuth2SuccessHandler;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf(csrf -> csrf.disable()) // NOSONAR S5870: Disabled because the application is stateless and uses JWT in the Authorization header.
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(request -> {

				request.requestMatchers(HttpMethod.GET, "/", "/api/translations/**").permitAll();
				request.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
				request.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
				request.requestMatchers(HttpMethod.POST, "/api/users").permitAll();
				request.requestMatchers(HttpMethod.GET, "/oauth2/**").permitAll();
				request.requestMatchers(HttpMethod.GET, "/auth/verify-email**").permitAll();
				request.anyRequest().authenticated();
			})
			.formLogin(Customizer.withDefaults())
			.oauth2Login(oauth -> oauth
				.successHandler(oAuth2SuccessHandler)
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(FRONTEND_BASE_URL));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);


		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
