package projekti.demo;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	
	//@Autowired
	private UserDetailsService userDetailsService; // type of attribute -> interface
	
	// Constructor injection
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService; 
	}

	private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
		new AntPathRequestMatcher("/h2-console/**")

	};

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

				http.authorizeHttpRequests(
        authorize -> authorize
        .requestMatchers(antMatcher("/css/**")).permitAll()

        .requestMatchers(WHITE_LIST_URLS).permitAll()

        .anyRequest().authenticated())

        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))

        .httpBasic(Customizer.withDefaults())

        .formLogin(formlogin -> formlogin.loginPage("/login")

            .defaultSuccessUrl("/index", true)

            .permitAll())

        .logout(logout -> logout.permitAll())

        .csrf(csrf -> csrf.disable())

        .cors(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*")); // Tää vaihetaan pyyntöä lähettävän domainiin nyt kaikki domainit sallittuja
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		configuration.setAllowCredentials(false);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
